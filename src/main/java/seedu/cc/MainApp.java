package seedu.cc;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import seedu.cc.commons.core.Config;
import seedu.cc.commons.core.LogsCenter;
import seedu.cc.commons.core.Version;
import seedu.cc.commons.exceptions.DataLoadingException;
import seedu.cc.commons.util.ConfigUtil;
import seedu.cc.commons.util.StringUtil;
import seedu.cc.logic.ClinicLogic;
import seedu.cc.logic.ClinicLogicManager;
import seedu.cc.model.ClinicBook;
import seedu.cc.model.NewModel;
import seedu.cc.model.NewModelManager;
import seedu.cc.model.NewReadOnlyUserPrefs;
import seedu.cc.model.NewUserPrefs;
import seedu.cc.model.ReadOnlyClinicBook;
import seedu.cc.model.util.NewSampleDataUtil;
import seedu.cc.storage.ClinicBookStorage;
import seedu.cc.storage.ClinicStorage;
import seedu.cc.storage.ClinicStorageManager;
import seedu.cc.storage.JsonClinicBookStorage;
import seedu.cc.storage.JsonNewUserPrefsStorage;
import seedu.cc.storage.NewUserPrefsStorage;
import seedu.cc.ui.NewUiManager;
import seedu.cc.ui.Ui;

/**
 * Runs the application.
 */
public class MainApp extends Application {

    public static final Version VERSION = new Version(0, 2, 2, true);

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    protected Ui ui;
    protected ClinicLogic logic;
    protected ClinicStorage storage;
    protected NewModel model;
    protected Config config;

    @Override
    public void init() throws Exception {
        logger.info("=============================[ Initializing AddressBook ]===========================");
        super.init();

        AppParameters appParameters = AppParameters.parse(getParameters());
        config = initConfig(appParameters.getConfigPath());
        initLogging(config);

        NewUserPrefsStorage userPrefsStorage = new JsonNewUserPrefsStorage(config.getUserPrefsFilePath());
        NewUserPrefs userPrefs = initPrefs(userPrefsStorage);
        ClinicBookStorage addressBookStorage = new JsonClinicBookStorage(userPrefs.getClinicBookFilePath());
        storage = new ClinicStorageManager(addressBookStorage, userPrefsStorage);

        model = initModelManager(storage, userPrefs);

        logic = new ClinicLogicManager(model, storage);

        ui = new NewUiManager(logic);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private NewModel initModelManager(ClinicStorage storage, NewReadOnlyUserPrefs userPrefs) {
        logger.info("Using data file : " + storage.getClinicBookFilePath());

        Optional<ReadOnlyClinicBook> addressBookOptional;
        ReadOnlyClinicBook initialData;
        try {
            addressBookOptional = storage.readClinicBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Creating a new data file " + storage.getClinicBookFilePath()
                    + " populated with a sample AddressBook.");
            }
            initialData = addressBookOptional.orElseGet(NewSampleDataUtil::getSampleClinicBook);
        } catch (DataLoadingException e) {
            logger.warning("Data file at " + storage.getClinicBookFilePath() + " could not be loaded."
                + " Will be starting with an empty AddressBook.");
            initialData = new ClinicBook();
        }

        return new NewModelManager(initialData, userPrefs);
    }

    private void initLogging(Config config) {
        LogsCenter.init(config);
    }

    /**
     * Returns a {@code Config} using the file at {@code configFilePath}. <br>
     * The default file path {@code Config#DEFAULT_CONFIG_FILE} will be used instead
     * if {@code configFilePath} is null.
     */
    protected Config initConfig(Path configFilePath) {
        Config initializedConfig;
        Path configFilePathUsed;

        configFilePathUsed = Config.DEFAULT_CONFIG_FILE;

        if (configFilePath != null) {
            logger.info("Custom Config file specified " + configFilePath);
            configFilePathUsed = configFilePath;
        }

        logger.info("Using config file : " + configFilePathUsed);

        try {
            Optional<Config> configOptional = ConfigUtil.readConfig(configFilePathUsed);
            if (!configOptional.isPresent()) {
                logger.info("Creating new config file " + configFilePathUsed);
            }
            initializedConfig = configOptional.orElse(new Config());
        } catch (DataLoadingException e) {
            logger.warning("Config file at " + configFilePathUsed + " could not be loaded."
                + " Using default config properties.");
            initializedConfig = new Config();
        }

        //Update config file in case it was missing to begin with or there are new/unused fields
        try {
            ConfigUtil.saveConfig(initializedConfig, configFilePathUsed);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }
        return initializedConfig;
    }

    /**
     * Returns a {@code UserPrefs} using the file at {@code storage}'s user prefs file path,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    protected NewUserPrefs initPrefs(NewUserPrefsStorage storage) {
        Path prefsFilePath = storage.getUserPrefsFilePath();
        logger.info("Using preference file : " + prefsFilePath);

        NewUserPrefs initializedPrefs;
        try {
            Optional<NewUserPrefs> prefsOptional = storage.readUserPrefs();
            if (!prefsOptional.isPresent()) {
                logger.info("Creating new preference file " + prefsFilePath);
            }
            initializedPrefs = prefsOptional.orElse(new NewUserPrefs());
        } catch (DataLoadingException e) {
            logger.warning("Preference file at " + prefsFilePath + " could not be loaded."
                + " Using default preferences.");
            initializedPrefs = new NewUserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            storage.saveUserPrefs(initializedPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initializedPrefs;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting AddressBook " + MainApp.VERSION);
        ui.start(primaryStage);
    }

    @Override
    public void stop() {
        logger.info("============================ [ Stopping Address Book ] =============================");
        try {
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException e) {
            logger.severe("Failed to save preferences " + StringUtil.getDetails(e));
        }
    }
}
