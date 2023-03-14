package mac;

import java.io.*;
import java.lang.*;
import mac.setSize;
import mac.CONF;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;


public class DTMFRequestSony extends MIDlet implements CommandListener,

        Runnable {

    /*
     - Konfigueringsfil --- initierar programmets
         - Språk, Ikoner och annan information.
     */

    private CONF config;


    private static String url = "socket://127.0.0.1:8100";
    private String request;
    private String response;
    public String ResponceMessage;
    private StringItem question;
    private String MEXOn;
    private setSize sizeScreen;
    private RecordStore addrBook;
    private static final int FN_LEN = 10;
    private static final int LN_LEN = 20;
    private static final int PN_LEN = 15;
    final private static int ERROR = 0;
    final private static int INFO = 1;
    final private static int SIZE = 2;
    final private static int INPUT = 3;
    final private static int NAMEERROR = 4;
    private Display display;

    private Alert alert, alertEditSettings, alertRestarting, alerterror,
    alertExit, alertON, alertOFF, alertDebugNOK;

    private int sortOrder = 1;

    private Ticker mainTicker, absentTicker, atExtTicker, backAtTicker,
    outUntilTicker, groupTicker, loginGroupTicker, logoffGroupTicker,
    callForwardTicker, allCallForwardTicker, externCallForwardTicker,
    internCallForwardTicker,

    allCallingForwardTicker, allBusyCallingForwardTicker,
    allNoAnswerCallingForwardTicker, allBusyNoAnswerCallingForwardTicker,

    externCallingForwardTicker, externBusyCallingForwardTicker,
    externNoAnswerCallingForwardTicker, externBusyNoAnswerCallingForwardTicker,

    internCallingForwardTicker, internBusyCallingForwardTicker,
    internNoAnswerCallingForwardTicker, internBusyNoAnswerCallingForwardTicker,
    voiceMailTicker;


    private Form editSettingForm, editSettingForm2,
    countryForm,
    editSystemForm_1, editSystemForm_2, editPersonalForm,
    atExtForm, backAtForm, outUntilForm, loginGroupForm, logoffGroupForm,
    allCallingForwardForm, allBusyCallingForwardForm,
    allNoAnswerCallingForwardForm,
    allBusyNoAnswerCallingForwardForm, externBusyCallingForwardForm,
    externNoAnswerCallingForwardForm, externBusyNoAnswerCallingForwardForm,
    externCallingForwardForm, internBusyCallingForwardForm,
    internNoAnswerCallingForwardForm, internBusyNoAnswerCallingForwardForm,
    internCallingForwardForm, preEditForm, voiceEditForm, debugForm,
            keyCodeForm,
    voiceMessageForm;

    private TextBox response_textbox;

    private TextField dateNumber, accessNumbers, editSwitchBoardNumber,
    editExtensionNumber, editPinCodeNumber,
    accessNumbers2, editSwitchBoardNumber2, countryField,

    editTextSystemTextField_1, editTextSystemTextField_2,
    editTextPersonalTextField,

    atExtTextField, backAtTextField, outUntilTextField,
    loginGroupTextField, logoffGroupTextField,

    allCallingForwardTextField, allBusyCallingForwardTextField,
    allNoAnswerCallingForwardTextField, allBusyNoAnswerCallingForwardTextField,

    externBusyCallingForwardTextField, externNoAnswerCallingForwardTextField,
    externBusyNoAnswerCallingForwardTextField, externCallingForwardTextField,

    internBusyCallingForwardTextField, internNoAnswerCallingForwardTextField,
    internBusyNoAnswerCallingForwardTextField, internCallingForwardTextField,
    preEditTextField, voiceEditTextField, voiceExtensionEditTextField,
    debugTextField, keyCodeTextField, voiceMessageTextField;

    public List pbx_List, pbx_List_type, mainList,
    language_List, absentList, groupList, callForwardList, allCallForwardList,
    externCallForwardList, internCallForwardList, voiceMailList,
    editSystemAbsentList;


    private Command
            AboutCommand, minimazeCommand, helpCommand, editSettingBackCommand,
    editSettingBackCommand2, editSettingCancelCommand2, editSettingSaveCommand2,
    editSettingCancelCommand, editSettingSaveCommand, propertiesCommand,
    backCommand, settingsCommand, settingsListCancelCommand,
    languageListCancelCommand,
    ExitCommandMainList, meny, goBackCommand,
    countryBackCommand, countryCancelCommand, countrySaveCommand,


    ExitCommandAbsentList, absentDialCommand, atExtBackCommand,
    backAtCommand, backAtDialCommand, outUntilDialCommand, outUntilBackCommand,

    groupBackCommand, loginGroupDialCommand,
    loginGroupBackCommand,
    logoffGroupDialCommand, logoffGroupBackCommand, backCallForwardCommand,
    backAllCallForwardCommand, backExternCallForwardCommand,
    backInternCallForwardCommand,

    allCallForwardDialCommand, allCallForwardBackCommand,
    allBusyCallForwardDialCommand,
    allBusyCallForwardBackCommand, allNoAnswerCallForwardDialCommand,
    allNoAnswerCallForwardBackCommand, allBusyNoAnswerCallForwardDialCommand,
    allBusyNoAnswerCallForwardBackCommand,

    externCallForwardDialCommand, externCallForwardBackCommand,
    externBusyCallForwardDialCommand,
    externBusyCallForwardBackCommand, externNoAnswerCallForwardDialCommand,
    externNoAnswerCallForwardBackCommand,
    externBusyNoAnswerCallForwardDialCommand,
    externBusyNoAnswerCallForwardBackCommand,

    internCallForwardDialCommand, internCallForwardBackCommand,
    internBusyCallForwardDialCommand,
    internBusyCallForwardBackCommand, internNoAnswerCallForwardDialCommand,
    internNoAnswerCallForwardBackCommand,
    internBusyNoAnswerCallForwardDialCommand,
    internBusyNoAnswerCallForwardBackCommand, voiceMailBackCommand,

    saveEditSystemCommand_1, cancelEditSystemCommand_1, saveEditSystemCommand_2,
    cancelEditSystemCommand_2,
    saveEditPersonalCommand, cancelEditPersonalCommand, editAbsentSystemCommand,
    backEditAbsentSystemCommand, preEditSaveCommand, preEditBackCommand,
    preEditCancelCommand,
    voiceEditSaveCommand, voiceEditBackcommand, voiceEditCancelCommand,
    confirmExitYESCommand, confirmExitNOCommand,
    settingsPBXListCancelCommand,
    Pbx_List_Type_backCommand,
    responseCancelCommand, clearCommand, responseUpdateCommand, debugOKCommand,
    debugCancelCommand, systemCommand, responseDebugOnCommand,
            responseDebugOffCommand,
    keyCodeCommand, keyCodeSaveCommand, keyCodeCancelCommand,
            aboutMobismaCommand,
    voiceMessageSaveCommand, voiceMessageCancelCommand, voiceMessageBackCommand;

    public String wrongmessage;
    private boolean socketok = false;
    private Command thCmd;
    private int type = 0;

    private String accessCode, internNumber;
    private String identy, checkIdenty;
    private String[] subStr;
    private String
    stringTotal, setMounth, setDate, setYear,
    DBdate, DBmounth, DByear, DBdateBack, DBmounthBack, DByearBack, getTWO,
    dateString, setViewMounth, ViewDateString, setdayBack,
    setmounthBack, setyearBack, accessNumber_PBX, switchBoardNumber_PBX,
    countryCode_PBX, extensionNumber_PBX, HGP_PBX, pinCodeNumber_PBX,
    precode_PBX, voiceMailSwitchboard_PBX, voiceMailOperator_PBX,
    mexONOFF_PBX, checkStatus_PBX, dbg_PBX, demo_PBX, companyName_PBX,
    namne_PBX, demoLicens, pbx_auto, pbx_pincode;

    public RecordStore recStore = null;
    static final String REC_STORE = "Data_Store_attendant_145";

    private int antalDagar;
    private int dayBack;
    private int mounthBack;
    private int yearBack;
    private int dayAfter;
    private int monthAfter;
    private int yearAfter;
    private int day;
    private int month;
    private int checkYear;

    private int saveRecordID;

    private Date today;
    private String todayDate;
    private TimeZone tz = TimeZone.getTimeZone("GMT+1");

    // Tillhör sensast slagna nummer.
    private String mainCallString;

    private String checkAlert, alertString;
    private String systemText_1_Attribute,
    systemText_2_Attribute, personalText_Attribute;
    public String lang_PBX;

    //==================== Generella ord/text i prg ============================

    private String dialDefaultDial_GEN = "Dial",
    settingsDefaultEnterpriseLicense_GEN = "Enterprise License";

    //==================== D A N I S H =========================================

    private String

            settingsDefaultAbout_DE = "Om mobisma",
    SettingsDefaultAccessPINcode_DE = "Adgang PBX PIN",
    extensionDefaultAddNew_DE = "Tilføj",
    callForwardDefaultAllCalls_DE = "Alle opkald",
    absentDefaultAtExt_DE = "Placeret på de links",
    settingsDefaultAutoAccess_DE = "Auto adgang PBX",
    genDefaultBack_DE = "Tilbage",
    absentDefaultBackAt_DE = "Tilbage Time",
    callForwardDefaultBusy_DE = "Optaget",
    callForwardDefaultBusyNoAnswer_DE = "Optaget / Intet svar",
    callDefaultCall_DE = "Ringe",
    extensionDefaultCall_DE = "Ring lokal numer",
    callForwardDefault_DE = "Viderstil",
    msgDefaultCallistIsEmpty_DE = "Opkaldslisten er tom",
    genDefaultCancel_DE = "Annuller",
    alertDefaultCantAddAnymoreExt_DE =
            "Det lykkedes ikke at tilføje lokal nr., max 25!",
    alertDefaultCouldntAddChangesEmtpyField_DE =
            "Det lykkedes ikke at tilføje, tomt felt",
    alertDefaultCouldntAddRecord_DE = "Det lykkedes ikke at tilføje lokal nr.",
    alertDefaultChangesSave_DE = "Ændringerne er gemt",
    mgsDefaultContactListIsEmpty_DE = "Listen over kontakter er tom",
    alertDefaultCountryCodeError_DE = "Fejl i landekode",
    settingsDefaultCountryCode_DE = "Landekode",
    genDefaultDelete_DE = "Slet",
    genDefaultDeleteAll_DE = "Slet alt",
    dialledCallsDefault_DE = "Kaldte numre",
    callForwardDefaultDontDisturb_DE = "Vil ikke forstyrres",
    genDefaultEdit_DE = "Rediger",
    settingsDefaultEditPBXAccess_DE = "Rediger pbx adgang",
    absentDefaultEditPresence_DE = "Redigér attribut",
    voiceMailDefaultEditVoicemail_DE = "Rediger Voicemail",
    enterDefaultEngerCharacter_DE = "Indtast tegn",
    enterDefaultEnterExtension_DE = "Indtast lokal nr.",
    enterDefaultEnterGroupNumber_DE = "Indtast gruppe nummer",
    enterDefaultEnterHHMM_DE = "Indtast TTMM",
    enterDefaultEnterNumber_DE = "Indtast nummer",
    alertDefaultErrorChangeTo_DE = "FEJL! skifte til",
    alertDefaultError_DE = "FEJL!",
    enterDefaultEnterMMDD_DE = "Indtast MMDD",
    exitDefaultExitTheProgramYesOrNo_DE = "Afslut programmet. Ja eller nej?",
    genDefaultExit_DE = "Ende",
    settingsDefaultExtension_DE = "Lokal nummer",
    callExtensionDefaultExtensionWasAdded_DE = "Lokal nr. tilføjet",
    callForwardDefaultExternCalls_DE = "Eksterne opkald",
    absentDefaultGoneHome_DE = "Gået for dagen",
    groupsDefaultGroups_DE = "Grupper",
    settingsDefaultHelp_DE = "Hjælp",
    absentDefaultInAMeeting_DE = "Møde",
    alertDefaultInfo_DE = "Info!",
    alertDefaultInstedOf_DE = "Istedet for",
    callForwardDefaultInternCalls_DE = "Interne samtaler",
    settingsDefaultLanguage_DE = "Sprog",
    settingsDefaultLineAccess_DE = "Line-Access",
    groupsDefaultLoginAllGroups_DE = "Log ind alle grupper",
    groupsDefaultLoginSpecificGroup_DE = "Log ind på en specifik gruppe",
    groupsDefaultLogoutAllGroups_DE = "Log ud af alle grupper",
    groupsDefaultLogoutSpecificGroup_DE = "Log ud af en specifik gruppe",
    alertDefaultMaxSize_DE = "MaxSize!",
    genDefaultMinimise_DE = "Minimer",
    callExtensionDefaultName_DE = "Navn",
    exitDefaultNo_DE = "Nej",
    callForwardDefaultNoAnswer_DE = "No answer",
    settingsDefaultOptions_DE = "Vælg",
    absentDefaultOutUntil_DE = "Ud Indtil",
    absentDefaultPersonalAtt_DE = "Personlige Attributter",
    settingsDefaultPINcode_DE = "PIN-kode",
    settingsDefaultPreEditCode_DE = "Rediger tegn",
    callForwardDefaultRemove_DE = "Slet",
    absentDefaultRemovePresence_DE = "Fjern reference",
    exitDefaultRestartProgram_DE = "Start programmet",
    alertDefaultSaveChanges_DE = "Gem ændringer",
    genDefaultSave_DE = "Gemme",
    settingsDefaultSelectCountryCode_DE = "Vælg landekoder",
    genDefaultSelect_DE = "Vælg",
    genDefaultSend_DE = "Sende",
    absentDefaultSetPresence_DE = "Set tilstedeværelse",
    settingsDefaultSettings_DE = "Indstillinger",
    callExtensionDefaultSurname_DE = "Efternavn",
    settingsDefaultSwitchboardNumber_DE = "Omstillingsbord nummer",
    absentDefaultSystemAttOne_DE = "System attribut 1",
    absentDeafaultSystemAttTwo_DE = "System attribut 2",
    absentDeafaultWillReturnSoon_DE = "Vil vende tilbage snart",
    alertDefaultWrongInputTryAgain_DE = "Forkert input! Prøv igen!",
    voiceMailDefaultVoiceMail_DE = "VoiceMail",
    genDefaultYes_DE = "Ja",

    accessPBXDefault_DE = "Adgang PBX",
    autoAccessDefault_DE = "Auto adgang PBX",
    accessViaPINCodeDefault_DE = "Adgang PBX PIN",
    dialDefault_DE = "Ringe (via PBX)";


    //==================== E N G L I S H  ======================================

    private String

            settingsDefaultAbout_ENG = "About mobisma",
    SettingsDefaultAccessPINcode_ENG = "Access PBX PIN-code",
    extensionDefaultAddNew_ENG = "Add new",
    callForwardDefaultAllCalls_ENG = "All calls",
    absentDefaultAtExt_ENG = "At Ext",
    settingsDefaultAutoAccess_ENG = "Auto Access PBX",
    genDefaultBack_ENG = "Back",
    absentDefaultBackAt_ENG = "Back at",
    callForwardDefaultBusy_ENG = "Busy",
    callForwardDefaultBusyNoAnswer_ENG = "Busy/No answer",
    callDefaultCall_ENG = "Call",
    extensionDefaultCall_ENG = "Call extension",
    callForwardDefault_ENG = "Call forward",
    msgDefaultCallistIsEmpty_ENG = "Call list is empty!",
    genDefaultCancel_ENG = "Cancel",
    alertDefaultCantAddAnymoreExt_ENG = "Can´t add anymore extensions, max 25!",
    alertDefaultCouldntAddChangesEmtpyField_ENG =
            "Couldn´t add changes, empty field",
    alertDefaultCouldntAddRecord_ENG = "Couldn´t add record",
    alertDefaultChangesSave_ENG = "\n\n\n...changes save... ",
    mgsDefaultContactListIsEmpty_ENG = "Contact list is empty!",
    alertDefaultCountryCodeError_ENG = "Country code error!",
    settingsDefaultCountryCode_ENG = "Country Code",
    genDefaultDelete_ENG = "Delete",
    genDefaultDeleteAll_ENG = "Delete All",
    dialledCallsDefault_ENG = "Dialled Calls",
    callForwardDefaultDontDisturb_ENG = "Don't disturb",
    genDefaultEdit_ENG = "Edit",
    settingsDefaultEditPBXAccess_ENG = "Edit PBX Access",
    absentDefaultEditPresence_ENG = "Edit Presence",
    voiceMailDefaultEditVoicemail_ENG = "Edit Voicemail",
    enterDefaultEngerCharacter_ENG = "Enter character:",
    enterDefaultEnterExtension_ENG = "Enter extension",
    enterDefaultEnterGroupNumber_ENG = "Enter group number",
    enterDefaultEnterHHMM_ENG = "Enter HHMM",
    enterDefaultEnterNumber_ENG = "Enter number",
    alertDefaultErrorChangeTo_ENG = "ERROR!\n\n change to",
    alertDefaultError_ENG = "Error!",
    enterDefaultEnterMMDD_ENG = "Enter MMDD",
    exitDefaultExitTheProgramYesOrNo_ENG = "Exit the program" + '?' +
                                           "\n Yes or No",
    genDefaultExit_ENG = "Exit",
    settingsDefaultExtension_ENG = "Extension:",
    callExtensionDefaultExtensionWasAdded_ENG = "Extension was added!",
    callForwardDefaultExternCalls_ENG = "Extern Calls",
    absentDefaultGoneHome_ENG = "Gone Home",
    groupsDefaultGroups_ENG = "Groups",
    settingsDefaultHelp_ENG = "Help",
    absentDefaultInAMeeting_ENG = "In a Meeting",
    alertDefaultInfo_ENG = "Info!",
    alertDefaultInstedOf_ENG = " instead of ",
    callForwardDefaultInternCalls_ENG = "Intern Calls",
    settingsDefaultLanguage_ENG = "Language",
    settingsDefaultLineAccess_ENG = "Line-access",
    groupsDefaultLoginAllGroups_ENG = "Login all groups",
    groupsDefaultLoginSpecificGroup_ENG = "Login Specific group",
    groupsDefaultLogoutAllGroups_ENG = "Logout all groups",
    groupsDefaultLogoutSpecificGroup_ENG = "Logout Specific group",
    alertDefaultMaxSize_ENG = "MaxSize!",
    genDefaultMinimise_ENG = "Minimise",
    callExtensionDefaultName_ENG = "Name:",
    exitDefaultNo_ENG = "No",
    callForwardDefaultNoAnswer_ENG = "No answer",
    settingsDefaultOptions_ENG = "Options",
    absentDefaultOutUntil_ENG = "Out Until",
    absentDefaultPersonalAtt_ENG = "Personal attribute",
    settingsDefaultPINcode_ENG = "PIN-code:",
    settingsDefaultPreEditCode_ENG = "Pre Edit Code",
    callForwardDefaultRemove_ENG = "Remove",
    absentDefaultRemovePresence_ENG = "Remove Presence",
    exitDefaultRestartProgram_ENG = "\n\n\n...Restart...Program...",
    alertDefaultSaveChanges_ENG = "Save changes",
    genDefaultSave_ENG = "Save",
    settingsDefaultSelectCountryCode_ENG = "Select Country Code",
    genDefaultSelect_ENG = "Select",
    genDefaultSend_ENG = "Send",
    absentDefaultSetPresence_ENG = "Set presence",
    settingsDefaultSettings_ENG = "Settings",
    callExtensionDefaultSurname_ENG = "Surname",
    settingsDefaultSwitchboardNumber_ENG = "Switchboard number:",
    absentDefaultSystemAttOne_ENG = "System attribute 1",
    absentDeafaultSystemAttTwo_ENG = "System attribute 2",
    absentDeafaultWillReturnSoon_ENG = "Will return Soon",
    alertDefaultWrongInputTryAgain_ENG = "Wrong input!\n Try again!",
    voiceMailDefaultVoiceMail_ENG = "VoiceMail",
    genDefaultYes_ENG = "Yes",

    accessPBXDefault_ENG = "Access PBX",
    autoAccessDefault_ENG = "Auto Access",
    accessViaPINCodeDefault_ENG = "Access via PIN-Code",
    dialDefault_ENG = "Call (via PBX)";


    //==================== F I N N I S H =======================================

    private String

            settingsDefaultAbout_FIN = "Tietoja mobismasta",
    SettingsDefaultAccessPINcode_FIN = "Syötä PBX PIN-koodi",
    extensionDefaultAddNew_FIN = "Lisää uusi",
    callForwardDefaultAllCalls_FIN = "Kaikki puhelut",
    absentDefaultAtExt_FIN = "Alanumerossa",
    settingsDefaultAutoAccess_FIN = "Autom. pääsy PBX",
    genDefaultBack_FIN = "Takaisin",
    absentDefaultBackAt_FIN = "Takaisin klo",
    callForwardDefaultBusy_FIN = "Varattu",
    callForwardDefaultBusyNoAnswer_FIN = "Varattu/Ei-vastaa",
    callDefaultCall_FIN = "Soita",
    extensionDefaultCall_FIN = "Soita alanumeroon",
    callForwardDefault_FIN = "Puhelun ennakkosiirto",
    msgDefaultCallistIsEmpty_FIN = "Puhelulista on tyhjä",
    genDefaultCancel_FIN = "Peruuta",
    alertDefaultCantAddAnymoreExt_FIN = "Ei voinu lisätää, vain 25 kpl",
    alertDefaultCouldntAddChangesEmtpyField_FIN = "Ei voinu lisätää,tyhjä olue",
    alertDefaultCouldntAddRecord_FIN = "Ei vionu lisätääalanumeron",
    alertDefaultChangesSave_FIN = "Vaihdot säästyy",
    mgsDefaultContactListIsEmpty_FIN = "Kontaktilista on tyhjä",
    alertDefaultCountryCodeError_FIN = "Väärä maan numero",
    settingsDefaultCountryCode_FIN = "Maannumero",
    genDefaultDelete_FIN = "Poista",
    genDefaultDeleteAll_FIN = "Poista kaikki",
    dialledCallsDefault_FIN = "Soitetut puhelut",
    callForwardDefaultDontDisturb_FIN = "Älä Häiritse",
    genDefaultEdit_FIN = "Muokkaa",
    settingsDefaultEditPBXAccess_FIN = "Toimittaa PBX Acess",
    absentDefaultEditPresence_FIN = "Muokkaa poissaoloa",
    voiceMailDefaultEditVoicemail_FIN = "Muokkaa puhepostia",
    enterDefaultEngerCharacter_FIN = "Syötä merkki",
    enterDefaultEnterExtension_FIN = "Syötä alanumero",
    enterDefaultEnterGroupNumber_FIN = "Syötä ryhmän n:o",
    enterDefaultEnterHHMM_FIN = "Syötä HHMM",
    enterDefaultEnterNumber_FIN = "Syötä numero",
    alertDefaultErrorChangeTo_FIN = "Väärin muuta lisäksi",
    alertDefaultError_FIN = "Värin",
    enterDefaultEnterMMDD_FIN = "Syötä DDMM",
    exitDefaultExitTheProgramYesOrNo_FIN = "Päätää ohjelman, jo tai ei",
    genDefaultExit_FIN = "Lopeta",
    settingsDefaultExtension_FIN = "Alaliittymä",
    callExtensionDefaultExtensionWasAdded_FIN = "Alaliittymä on lisätty",
    callForwardDefaultExternCalls_FIN = "Ulkopuhelut",
    absentDefaultGoneHome_FIN = "Mennyt kotiin",
    groupsDefaultGroups_FIN = "Ryhmät",
    settingsDefaultHelp_FIN = "Apua",
    absentDefaultInAMeeting_FIN = "Kokous",
    alertDefaultInfo_FIN = "Info!",
    alertDefaultInstedOf_FIN = "joku muu, toinen",
    callForwardDefaultInternCalls_FIN = "Sisäpuhelut",
    settingsDefaultLanguage_FIN = "Kieli",
    settingsDefaultLineAccess_FIN = "Ulossoittotunnus",
    groupsDefaultLoginAllGroups_FIN = "Liity kaikkiin ryhmiin",
    groupsDefaultLoginSpecificGroup_FIN = "Liity tiettyyn ryhmään",
    groupsDefaultLogoutAllGroups_FIN = "Poistu kaikista ryhmistä",
    groupsDefaultLogoutSpecificGroup_FIN = "Poistu tietystä ryhmästä",
    alertDefaultMaxSize_FIN = "Issoin koko",
    genDefaultMinimise_FIN = "Pienennä",
    callExtensionDefaultName_FIN = "Nimi",
    exitDefaultNo_FIN = "Ei",
    callForwardDefaultNoAnswer_FIN = "Ei vastaa",
    settingsDefaultOptions_FIN = "Valinnat",
    absentDefaultOutUntil_FIN = "Palaan pvm",
    absentDefaultPersonalAtt_FIN = "Personoitu",
    settingsDefaultPINcode_FIN = "PIN-koodi",
    settingsDefaultPreEditCode_FIN = "Esimuokkauskoodi",
    callForwardDefaultRemove_FIN = "Poista",
    absentDefaultRemovePresence_FIN = "Poista poissaolokoodi",
    exitDefaultRestartProgram_FIN = "Käynnistä ohjelma uudelleen",
    alertDefaultSaveChanges_FIN = "Sässtä muutokset",
    genDefaultSave_FIN = "Tallenna",
    settingsDefaultSelectCountryCode_FIN = "Valite maan numero",
    genDefaultSelect_FIN = "Valitse",
    genDefaultSend_FIN = "Lähetä",
    absentDefaultSetPresence_FIN = "Aseta poissaolo",
    settingsDefaultSettings_FIN = "Asetukset",
    callExtensionDefaultSurname_FIN = "Sukunimi",
    settingsDefaultSwitchboardNumber_FIN = "Sisäänsoittonumero",
    absentDefaultSystemAttOne_FIN = "Järjestelmäkohtainen 1",
    absentDeafaultSystemAttTwo_FIN = "Järjestelmäkohtainen 2",
    absentDeafaultWillReturnSoon_FIN = "Palaan pian",
    alertDefaultWrongInputTryAgain_FIN = "Väärin, koita uudestaan",
    voiceMailDefaultVoiceMail_FIN = "Puheposti",
    genDefaultYes_FIN = "Kyllä",
    accessPBXDefault_FIN = "PBX sisäänpääsy",
    autoAccessDefault_FIN = "Auto Acess PBX",
    accessViaPINCodeDefault_FIN = "Laita Acess PBX-PINkodi",
    dialDefault_FIN = "Puhelu (PBX kautta)";


    //==================== F R E N C H =========================================

    private String

            settingsDefaultAbout_FRA = "Au sujet de Mobisma",
    SettingsDefaultAccessPINcode_FRA = "PIN Code Accès PBX",
    extensionDefaultAddNew_FRA = "Ajout nouveau",
    callForwardDefaultAllCalls_FRA = "Tous les appels",
    absentDefaultAtExt_FRA = "Au poste",
    settingsDefaultAutoAccess_FRA = "Code accès auto PBX",
    genDefaultBack_FRA = "Retour",
    absentDefaultBackAt_FRA = "Retour à",
    callForwardDefaultBusy_FRA = "Sur Occupation", // Sur Occupation
    callForwardDefaultBusyNoAnswer_FRA = "Occupation/Non Réponse", // Occupation/Non Réponse
    callDefaultCall_FRA = "Appel",
    extensionDefaultCall_FRA = "Appel poste",
    callForwardDefault_FRA = "Renvoi", // Renvoi
    msgDefaultCallistIsEmpty_FRA = "Liste appel vide",
    genDefaultCancel_FRA = "Annuler",
    alertDefaultCantAddAnymoreExt_FRA = "Nombre maxi de poste atteint (25)!",
    alertDefaultCouldntAddChangesEmtpyField_FRA =
            "Impossible de rajouter des modifications",
    alertDefaultCouldntAddRecord_FRA =
            "Impossible de rajouter des enregistrements",
    alertDefaultChangesSave_FRA = "Sauvegarder des modifications",
    mgsDefaultContactListIsEmpty_FRA = "Liste contact vide",
    alertDefaultCountryCodeError_FRA = "Erreur code pays!",
    settingsDefaultCountryCode_FRA = "Code pays",
    genDefaultDelete_FRA = "Effacer",
    genDefaultDeleteAll_FRA = "Tout effacer",
    dialledCallsDefault_FRA = "N° Composés", // N° Composés
    callForwardDefaultDontDisturb_FRA = "Ne pas déranger",
    genDefaultEdit_FRA = "Editer",
    settingsDefaultEditPBXAccess_FRA = "Editer Accès PBX",
    absentDefaultEditPresence_FRA = "Editer Présence",
    voiceMailDefaultEditVoicemail_FRA = "Editer Messagerie",
    enterDefaultEngerCharacter_FRA = "Insérer caractères",
    enterDefaultEnterExtension_FRA = "Insérer poste",
    enterDefaultEnterGroupNumber_FRA = "Insérer numéro groupe",
    enterDefaultEnterHHMM_FRA = "Insérer HHMM",
    enterDefaultEnterNumber_FRA = "Insérer numéro",
    alertDefaultErrorChangeTo_FRA = "Erreur! Modifier par",
    alertDefaultError_FRA = "Erreur!",
    enterDefaultEnterMMDD_FRA = "Insérer MMJJ",
    exitDefaultExitTheProgramYesOrNo_FRA = "Quitter application Oui ou Non",
    genDefaultExit_FRA = "Quitter",
    settingsDefaultExtension_FRA = "Poste",
    callExtensionDefaultExtensionWasAdded_FRA = "Poste est ajouter",
    callForwardDefaultExternCalls_FRA = "Appels externes",
    absentDefaultGoneHome_FRA = "Rentrer chez soi",
    groupsDefaultGroups_FRA = "Groupes",
    settingsDefaultHelp_FRA = "Aide",
    absentDefaultInAMeeting_FRA = "En réunion",
    alertDefaultInfo_FRA = "Info!", // Info!
    alertDefaultInstedOf_FRA = "au lieu de",
    callForwardDefaultInternCalls_FRA = "Appels internes",
    settingsDefaultLanguage_FRA = "Langage",
    settingsDefaultLineAccess_FRA = "Code accès lignes",
    groupsDefaultLoginAllGroups_FRA = "Présent tous les groupes",
    groupsDefaultLoginSpecificGroup_FRA = "Présent groupe spécifique",
    groupsDefaultLogoutAllGroups_FRA = "Absent tous les groupes",
    groupsDefaultLogoutSpecificGroup_FRA = "Absent groupe spécifique",
    alertDefaultMaxSize_FRA = "Taille maxi!",
    genDefaultMinimise_FRA = "Minimiser",
    callExtensionDefaultName_FRA = "Nom",
    exitDefaultNo_FRA = "Non", // Non
    callForwardDefaultNoAnswer_FRA = "Non réponse",
    settingsDefaultOptions_FRA = "Options",
    absentDefaultOutUntil_FRA = "En déplacement jusqu'au",
    absentDefaultPersonalAtt_FRA = "Message personnel",
    settingsDefaultPINcode_FRA = "Code PIN",
    settingsDefaultPreEditCode_FRA = "Editer Code Fonction",
    callForwardDefaultRemove_FRA = "Annuler", // Annuler
    absentDefaultRemovePresence_FRA = "Retirer présence",
    exitDefaultRestartProgram_FRA = "Redémarrer program",
    alertDefaultSaveChanges_FRA = "Modification sauvegarder",
    genDefaultSave_FRA = "Sauvegarder",
    settingsDefaultSelectCountryCode_FRA = "Sélectionner code Pays",
    genDefaultSelect_FRA = "Sélectionner",
    genDefaultSend_FRA = "Envoyer",
    absentDefaultSetPresence_FRA = "Réglage présence",
    settingsDefaultSettings_FRA = "Réglages",
    callExtensionDefaultSurname_FRA = "Prénom",
    settingsDefaultSwitchboardNumber_FRA = "Numéro SDA",
    absentDefaultSystemAttOne_FRA = "Message 1 système",
    absentDeafaultSystemAttTwo_FRA = "Message 2 système",
    absentDeafaultWillReturnSoon_FRA = "Retour dans 5 min",
    alertDefaultWrongInputTryAgain_FRA = "Insertion incorrecte!",
    voiceMailDefaultVoiceMail_FRA = "Voice mail",
    genDefaultYes_FRA = "Oui",
    accessPBXDefault_FRA = "Accès PBX",
    autoAccessDefault_FRA = "Code accès auto PBX",
    accessViaPINCodeDefault_FRA = "PIN Code Accès PBX",
    dialDefault_FRA = "Appel (via PBX)";


    //==================== S W E D I S H =======================================

    private String

            settingsDefaultAbout_SE = "Om mobisma",
    SettingsDefaultAccessPINcode_SE = "Access PBX PIN-kod",
    extensionDefaultAddNew_SE = "Lägg till",
    callForwardDefaultAllCalls_SE = "Alla samtal",
    absentDefaultAtExt_SE = "Finns på anknytning",
    settingsDefaultAutoAccess_SE = "Auto Access PBX",
    genDefaultBack_SE = "Bakåt",
    absentDefaultBackAt_SE = "Åter Klockan",
    callForwardDefaultBusy_SE = "Upptagen",
    callForwardDefaultBusyNoAnswer_SE = "Upptagen/Inget svar",
    callDefaultCall_SE = "Ring",
    extensionDefaultCall_SE = "Ring anknytning",
    callForwardDefault_SE = "Vidarekoppla",
    msgDefaultCallistIsEmpty_SE = "Samtalslistan är tom",
    genDefaultCancel_SE = "Avbryt",
    alertDefaultCantAddAnymoreExt_SE =
            "Kunde inte lägga till anknytning, max 25 st!",
    alertDefaultCouldntAddChangesEmtpyField_SE =
            "Kunde inte lägga till, tomt fält",
    alertDefaultCouldntAddRecord_SE = "Kunde inte lägga till anknytning",
    alertDefaultChangesSave_SE = "\n\n\n...Ändringarna sparas...",
    mgsDefaultContactListIsEmpty_SE = "Kontaktlistan är tom",
    alertDefaultCountryCodeError_SE = "Fel landsnummer",
    settingsDefaultCountryCode_SE = "Landsnummer",
    genDefaultDelete_SE = "Radera",
    genDefaultDeleteAll_SE = "Radera alla",
    dialledCallsDefault_SE = "Uppringda nummer",
    callForwardDefaultDontDisturb_SE = "Stör ej",
    genDefaultEdit_SE = "Redigera",
    settingsDefaultEditPBXAccess_SE = "Redigera PBX Access",
    absentDefaultEditPresence_SE = "Redigera attribut",
    voiceMailDefaultEditVoicemail_SE = "Redigera röstbrevlådan",
    enterDefaultEngerCharacter_SE = "Ange tecken",
    enterDefaultEnterExtension_SE = "Ange anknytning",
    enterDefaultEnterGroupNumber_SE = "Ange gruppnummer",
    enterDefaultEnterHHMM_SE = "Ange TTMM",
    enterDefaultEnterNumber_SE = "Ange nummer",
    alertDefaultErrorChangeTo_SE = "FEL! Ändra till",
    alertDefaultError_SE = "FEL!",
    enterDefaultEnterMMDD_SE = "Ange MMDD",
    exitDefaultExitTheProgramYesOrNo_SE = "Avsluta programmet Ja eller Nej",
    genDefaultExit_SE = "Avsluta",
    settingsDefaultExtension_SE = "Anknytning",
    callExtensionDefaultExtensionWasAdded_SE = "Anknytning adderad",
    callForwardDefaultExternCalls_SE = "Extern samtal",
    absentDefaultGoneHome_SE = "Gått för dagen",
    groupsDefaultGroups_SE = "Grupper",
    settingsDefaultHelp_SE = "Hjälp",
    absentDefaultInAMeeting_SE = "På Möte",
    alertDefaultInfo_SE = "Info!",
    alertDefaultInstedOf_SE = "Istället för",
    callForwardDefaultInternCalls_SE = "Intern samtal",
    settingsDefaultLanguage_SE = "Språk",
    settingsDefaultLineAccess_SE = "Linje-access",
    groupsDefaultLoginAllGroups_SE = "Logga in alla grupper",
    groupsDefaultLoginSpecificGroup_SE = "Logga in i specifik grupp",
    groupsDefaultLogoutAllGroups_SE = "Logga ur alla grupper",
    groupsDefaultLogoutSpecificGroup_SE = "Logga ut ur specifik grupp",
    alertDefaultMaxSize_SE = "MaxSize!",
    genDefaultMinimise_SE = "Minimera",
    callExtensionDefaultName_SE = "Namn",
    exitDefaultNo_SE = "Nej",
    callForwardDefaultNoAnswer_SE = "Inget svar",
    settingsDefaultOptions_SE = "Välj",
    absentDefaultOutUntil_SE = "Åter den",
    absentDefaultPersonalAtt_SE = "Personligt attribut",
    settingsDefaultPINcode_SE = "PIN-kod",
    settingsDefaultPreEditCode_SE = "Redigera Tecken",
    callForwardDefaultRemove_SE = "Ta bort",
    absentDefaultRemovePresence_SE = "Ta bort hänvisning",
    exitDefaultRestartProgram_SE = "\n\n\n...Starta.... om programmet...",
    alertDefaultSaveChanges_SE = "Spara ändringar",
    genDefaultSave_SE = "Spara",
    settingsDefaultSelectCountryCode_SE = "Välj landsnummer",
    genDefaultSelect_SE = "Välj",
    genDefaultSend_SE = "Skicka",
    absentDefaultSetPresence_SE = "Hänvisa",
    settingsDefaultSettings_SE = "Inställningar",
    callExtensionDefaultSurname_SE = "Efternamn",
    settingsDefaultSwitchboardNumber_SE = "Växelnummer",
    absentDefaultSystemAttOne_SE = "System attribut 1",
    absentDeafaultSystemAttTwo_SE = "System attribut 2",
    absentDeafaultWillReturnSoon_SE = "Strax tillbaka",
    alertDefaultWrongInputTryAgain_SE = "Fel inmatning! Försök igen!",
    voiceMailDefaultVoiceMail_SE = "Röstbrevlådan",
    genDefaultYes_SE = "Ja",

    accessPBXDefault_SE = "Access PBX",
    autoAccessDefault_SE = "Auto Access PBX",
    accessViaPINCodeDefault_SE = "Access PBX PIN-kod",
    dialDefault_SE = "Ring (via PBX)";


    //================== D U T C H =============================================

    private String

            settingsDefaultAbout_DU = "Over Mobisma",
    SettingsDefaultAccessPINcode_DU = "Pincode voor toegang PBX",
    extensionDefaultAddNew_DU = "Voeg nieuwe toe",
    callForwardDefaultAllCalls_DU = "Alle gesprekken",
    absentDefaultAtExt_DU = "Bij toestel",
    settingsDefaultAutoAccess_DU = "Automatisch toegang tot PBX",
    genDefaultBack_DU = "Terug",
    absentDefaultBackAt_DU = "Terug om",
    callForwardDefaultBusy_DU = "Bezet",
    callForwardDefaultBusyNoAnswer_DU = "Bezet/Geen Antwoord",
    callDefaultCall_DU = "Bellen",
    extensionDefaultCall_DU = "Bel toestel",
    callForwardDefault_DU = "Gesprek Doorschakelen",
    msgDefaultCallistIsEmpty_DU = "Gesprekslijst is leeg",
    genDefaultCancel_DU = "Annuleren",
    alertDefaultCantAddAnymoreExt_DU =
            "Kan geen toetellen meer toevoegen, max. 25!",
    alertDefaultCouldntAddChangesEmtpyField_DU =
            "Kon geen wijzigingen toevoegen, leeg veld",
    alertDefaultCouldntAddRecord_DU = "Kon niets toevoegen",
    alertDefaultChangesSave_DU = "\n\n\n...Opslaan wijzigingen...",
    mgsDefaultContactListIsEmpty_DU = "Contactenlijst is leeg",
    alertDefaultCountryCodeError_DU = "Landcode fout!",
    settingsDefaultCountryCode_DU = "Landcode",
    genDefaultDelete_DU = "Verwijder",
    genDefaultDeleteAll_DU = "Verwijder alles",
    dialledCallsDefault_DU = "Gekozen gesprekken",
    callForwardDefaultDontDisturb_DU = "Niet storen",
    genDefaultEdit_DU = "Wijzig",
    settingsDefaultEditPBXAccess_DU = "Wijzig PBX toegang",
    absentDefaultEditPresence_DU = "Wijzig Presence",
    voiceMailDefaultEditVoicemail_DU = "Wijzig Voice Mail",
    enterDefaultEngerCharacter_DU = "Voer karakter in",
    enterDefaultEnterExtension_DU = "Voer toestelnummer in",
    enterDefaultEnterGroupNumber_DU = "Voer groepsnummer in",
    enterDefaultEnterHHMM_DU = "Voer in HHMM",
    enterDefaultEnterNumber_DU = "Voer nummer in",
    alertDefaultErrorChangeTo_DU = "Fout! Wijzig in",
    alertDefaultError_DU = "Fout!",
    enterDefaultEnterMMDD_DU = "Voer in  MMDD",
    exitDefaultExitTheProgramYesOrNo_DU = "Beeïndig het programma Ja of Nee?",
    genDefaultExit_DU = "Einde",
    settingsDefaultExtension_DU = "Toestel",
    callExtensionDefaultExtensionWasAdded_DU = "Toestel is toegevoegd",
    callForwardDefaultExternCalls_DU = "Externe Gesprekken",
    absentDefaultGoneHome_DU = "Naar huis",
    groupsDefaultGroups_DU = "Groepen",
    settingsDefaultHelp_DU = "Help",
    absentDefaultInAMeeting_DU = "In een meeting",
    alertDefaultInfo_DU = "Informatie!",
    alertDefaultInstedOf_DU = " in plaats van ",
    callForwardDefaultInternCalls_DU = "Interne Gesprekken",
    settingsDefaultLanguage_DU = "Taal",
    settingsDefaultLineAccess_DU = "Netlijn toegang",
    groupsDefaultLoginAllGroups_DU = "Inloggen alle groepen",
    groupsDefaultLoginSpecificGroup_DU = "Inloggen specifieke groep",
    groupsDefaultLogoutAllGroups_DU = "Uitloggen alle groepen",
    groupsDefaultLogoutSpecificGroup_DU = "Uitloggen specifieke groep",
    alertDefaultMaxSize_DU = "Maximale groote!",
    genDefaultMinimise_DU = "Verkleinen",
    callExtensionDefaultName_DU = "Naam",
    exitDefaultNo_DU = "Nee",
    callForwardDefaultNoAnswer_DU = "Geen antwoord",
    settingsDefaultOptions_DU = "Optie's",
    absentDefaultOutUntil_DU = "Afwezig tot",
    absentDefaultPersonalAtt_DU = "Persoonlijke afwezigheidsboodschap",
    settingsDefaultPINcode_DU = "Pin-Code",
    settingsDefaultPreEditCode_DU = "Standaard Functie Code",
    callForwardDefaultRemove_DU = "Verwijder",
    absentDefaultRemovePresence_DU = "Verwijder Presence",
    exitDefaultRestartProgram_DU = "\n\n\n...Herstart.... programma",
    alertDefaultSaveChanges_DU = "Sla wijzigingen op",
    genDefaultSave_DU = "Opslaan",
    settingsDefaultSelectCountryCode_DU = "Selecteer de landcode",
    genDefaultSelect_DU = "Selecteer",
    genDefaultSend_DU = "Verstuur",
    absentDefaultSetPresence_DU = "Stel Presence in",
    settingsDefaultSettings_DU = "Instellingen",
    callExtensionDefaultSurname_DU = "Achternaam",
    settingsDefaultSwitchboardNumber_DU = "Telefoonnummer van de PBX",
    absentDefaultSystemAttOne_DU = "Systeem afwezigheidsboodschap 1",
    absentDeafaultSystemAttTwo_DU = "Systeem afwezigheidsboodschap 2",
    absentDeafaultWillReturnSoon_DU = "Ben zo terug",
    alertDefaultWrongInputTryAgain_DU = "Verkeerde invoer! Probeer opnieuw!",
    voiceMailDefaultVoiceMail_DU = "Voice Mail",
    genDefaultYes_DU = "Ja",

    accessPBXDefault_DU = "Toegang PBX",
    autoAccessDefault_DU = "Automatisch toegang tot PBX",
    accessViaPINCodeDefault_DU = "Pincode voor toegang PBX",
    dialDefault_DU = "Bellen (via PBX)";


    //================= G E R M A N ============================================

    private String

            settingsDefaultAbout_GE = "Über Mobisma",
    SettingsDefaultAccessPINcode_GE = "Zugangscode PBX",
    extensionDefaultAddNew_GE = "Hinzufügen",
    callForwardDefaultAllCalls_GE = "Alle Anrufe",
    absentDefaultAtExt_GE = "An Nebenstelle",
    settingsDefaultAutoAccess_GE = "Zugang über CLIP-Routing",
    genDefaultBack_GE = "Zurück",
    absentDefaultBackAt_GE = "Zurück um",
    callForwardDefaultBusy_GE = "Besetzt",
    callForwardDefaultBusyNoAnswer_GE = "Besetzt/Keine Antwort",
    callDefaultCall_GE = "Anruf",
    extensionDefaultCall_GE = "Nebenstelle Anrufen",
    callForwardDefault_GE = "Weiterleitung",
    msgDefaultCallistIsEmpty_GE = "Anrufliste ist leer",
    genDefaultCancel_GE = "Abbruch",
    alertDefaultCantAddAnymoreExt_GE =
            "Keine weiteren Nebenstellen möglich, max 25!",
    alertDefaultCouldntAddChangesEmtpyField_GE =
            "Änderung nicht möglich, Feld leer",
    alertDefaultCouldntAddRecord_GE = "Eintrag kann nicht hinzugefügt werden",
    alertDefaultChangesSave_GE = "Änderungen gespeichert",
    mgsDefaultContactListIsEmpty_GE = "Kontaktliste ist leer",
    alertDefaultCountryCodeError_GE = "Falscher Ländercode",
    settingsDefaultCountryCode_GE = "Ländercode",
    genDefaultDelete_GE = "Löschen",
    genDefaultDeleteAll_GE = "Alles löschen",
    dialledCallsDefault_GE = "Gewählte Nummern",
    callForwardDefaultDontDisturb_GE = "Nicht stören",
    genDefaultEdit_GE = "Bearbeiten",
    settingsDefaultEditPBXAccess_GE = "PBX Zugriff ändern",
    absentDefaultEditPresence_GE = "Anwesenheit bearbeiten",
    voiceMailDefaultEditVoicemail_GE = "Voicemail bearbeiten",
    enterDefaultEngerCharacter_GE = "Zeichen eingeben",
    enterDefaultEnterExtension_GE = "Nebenstelle eingeben",
    enterDefaultEnterGroupNumber_GE = "Gruppennummer eingeben",
    enterDefaultEnterHHMM_GE = "Zeit eingeben SSMM",
    enterDefaultEnterNumber_GE = "Nummer eingeben",
    alertDefaultErrorChangeTo_GE = "FEHLER! Ersetzen durch",
    alertDefaultError_GE = "Fehler!",
    enterDefaultEnterMMDD_GE = "Datum eingeben MMTT",
    exitDefaultExitTheProgramYesOrNo_GE = "Programm beenden - Ja oder Nein",
    genDefaultExit_GE = "Beenden",
    settingsDefaultExtension_GE = "Nebenstelle",
    callExtensionDefaultExtensionWasAdded_GE = "Nebenstelle hinzugefügt",
    callForwardDefaultExternCalls_GE = "Externe Anrufe",
    absentDefaultGoneHome_GE = "Bereits gegangen",
    groupsDefaultGroups_GE = "Gruppen",
    settingsDefaultHelp_GE = "Hilfe",
    absentDefaultInAMeeting_GE = "Im Meeting",
    alertDefaultInfo_GE = "Info!",
    alertDefaultInstedOf_GE = "anstelle von",
    callForwardDefaultInternCalls_GE = "Interne Anrufe",
    settingsDefaultLanguage_GE = "Sprache",
    settingsDefaultLineAccess_GE = "Amtszugang",
    groupsDefaultLoginAllGroups_GE = "Login - Alle Gruppen",
    groupsDefaultLoginSpecificGroup_GE = "Login - Bestimmte Gruppe",
    groupsDefaultLogoutAllGroups_GE = "Logout - Alle Gruppen",
    groupsDefaultLogoutSpecificGroup_GE = "Logout - Bestimmte Gruppe",
    alertDefaultMaxSize_GE = "Maximale Größe!",
    genDefaultMinimise_GE = "Minimieren",
    callExtensionDefaultName_GE = "Name",
    exitDefaultNo_GE = "Nein",
    callForwardDefaultNoAnswer_GE = "Keine Antwort",
    settingsDefaultOptions_GE = "Optionen",
    absentDefaultOutUntil_GE = "Abwesend bis",
    absentDefaultPersonalAtt_GE = "Persönliches Merkmal",
    settingsDefaultPINcode_GE = "PIN-Code",
    settingsDefaultPreEditCode_GE = "Funktionscode",
    callForwardDefaultRemove_GE = "Löschen",
    absentDefaultRemovePresence_GE = "Abwesenheit löschen",
    exitDefaultRestartProgram_GE = "Programm Neustart",
    alertDefaultSaveChanges_GE = "Änderungen speichern",
    genDefaultSave_GE = "Speichern",
    settingsDefaultSelectCountryCode_GE = "Ländercode auswählen",
    genDefaultSelect_GE = "Auswählen",
    genDefaultSend_GE = "Senden",
    absentDefaultSetPresence_GE = "Abwesenheit aktivieren",
    settingsDefaultSettings_GE = "Einstellungen",
    callExtensionDefaultSurname_GE = "Nachname",
    settingsDefaultSwitchboardNumber_GE = "Einwahlnummer",
    absentDefaultSystemAttOne_GE = "Systemattribut 1",
    absentDeafaultSystemAttTwo_GE = "Systemattribut 2",
    absentDeafaultWillReturnSoon_GE = "Bald zurück",
    alertDefaultWrongInputTryAgain_GE = "Falsche Eingabe! Nochmal versuchen!",
    voiceMailDefaultVoiceMail_GE = "Voicemail",
    genDefaultYes_GE = "Ja",

    accessPBXDefault_GE = "Zugang PBX",
    autoAccessDefault_GE = "Zugang über CLIP-Routing",
    accessViaPINCodeDefault_GE = "Zugangscode PBX",
    dialDefault_GE = "Anwahl (über PBX)";


    //================= N O R W A Y ============================================

    private String

            settingsDefaultAbout_NO = "Om mobisma",
    SettingsDefaultAccessPINcode_NO = "Access PBX PIN - kode",
    extensionDefaultAddNew_NO = "Tilføy ny",
    callForwardDefaultAllCalls_NO = "Alle anrop",
    absentDefaultAtExt_NO = "Ligger på linkene",
    settingsDefaultAutoAccess_NO = "Auto tilgang PBX",
    genDefaultBack_NO = "Tilbake",
    absentDefaultBackAt_NO = "Tilbake klokken",
    callForwardDefaultBusy_NO = "Opptatt",
    callForwardDefaultBusyNoAnswer_NO = "Opptatt / Nei svar",
    callDefaultCall_NO = "Ringe",
    extensionDefaultCall_NO = "Ring forlengelse",
    callForwardDefault_NO = "Viderekoble",
    msgDefaultCallistIsEmpty_NO = "Samtalelisten er tom",
    genDefaultCancel_NO = "Avbryt",
    alertDefaultCantAddAnymoreExt_NO =
            "Kan ikke legge til mer utvidelser, max 25!",
    alertDefaultCouldntAddChangesEmtpyField_NO =
            "Kunne ikke legge til endringene, tomme feltet",
    alertDefaultCouldntAddRecord_NO = "Kunne ikke legge til tilknytning",
    alertDefaultChangesSave_NO = "Endringene lagres",
    mgsDefaultContactListIsEmpty_NO = "Kontaktlisten er tom",
    alertDefaultCountryCodeError_NO = "Feil landskode",
    settingsDefaultCountryCode_NO = "Landskode",
    genDefaultDelete_NO = "Slette",
    genDefaultDeleteAll_NO = "Slett alle",
    dialledCallsDefault_NO = "Oppringte numre",
    callForwardDefaultDontDisturb_NO = "Ikke forstyrr",
    genDefaultEdit_NO = "Rediger",
    settingsDefaultEditPBXAccess_NO = "Rediger PBX - tilgang",
    absentDefaultEditPresence_NO = "Rediger tilstedeværelse",
    voiceMailDefaultEditVoicemail_NO = "Rediger talepost",
    enterDefaultEngerCharacter_NO = "Skriv inn tegnene",
    enterDefaultEnterExtension_NO = "Angi linkene",
    enterDefaultEnterGroupNumber_NO = "Angi gruppe - nummer",
    enterDefaultEnterHHMM_NO = "Tast TTMM",
    enterDefaultEnterNumber_NO = "Angi kode",
    alertDefaultErrorChangeTo_NO = "Feil!Endre til",
    alertDefaultError_NO = "Feil!",
    enterDefaultEnterMMDD_NO = "Angi MMDD",
    exitDefaultExitTheProgramYesOrNo_NO = "Avslutt programmet.Ja eller nei?",
    genDefaultExit_NO = "Avslutt",
    settingsDefaultExtension_NO = "Telefon Links",
    callExtensionDefaultExtensionWasAdded_NO = "Linker lagt",
    callForwardDefaultExternCalls_NO = "Eksterne anrop",
    absentDefaultGoneHome_NO = "Gått for dagen",
    groupsDefaultGroups_NO = "Grupper",
    settingsDefaultHelp_NO = "Hjelp",
    absentDefaultInAMeeting_NO = "Møte",
    alertDefaultInfo_NO = "Info!",
    alertDefaultInstedOf_NO = "I stedet for",
    callForwardDefaultInternCalls_NO = "Interne samtaler",
    settingsDefaultLanguage_NO = "Språk",
    settingsDefaultLineAccess_NO = "Linje - tilgang",
    groupsDefaultLoginAllGroups_NO = "Logg inn alle grupper",
    groupsDefaultLoginSpecificGroup_NO = "Logg inn på en bestemt gruppe",
    groupsDefaultLogoutAllGroups_NO = "Logg ut av alle grupper",
    groupsDefaultLogoutSpecificGroup_NO = "Logg ut av en bestemt gruppe",
    alertDefaultMaxSize_NO = "Maks størrelse!",
    genDefaultMinimise_NO = "Minimer",
    callExtensionDefaultName_NO = "Navn",
    exitDefaultNo_NO = "Nei",
    callForwardDefaultNoAnswer_NO = "Ingen svar",
    settingsDefaultOptions_NO = "Velg",
    absentDefaultOutUntil_NO = "Ute til",
    absentDefaultPersonalAtt_NO = "Personlig Attributter",
    settingsDefaultPINcode_NO = "PIN - kode",
    settingsDefaultPreEditCode_NO = "Rediger Karakterer",
    callForwardDefaultRemove_NO = "Slette",
    absentDefaultRemovePresence_NO = "Fjern referanse",
    exitDefaultRestartProgram_NO = "Start programmet",
    alertDefaultSaveChanges_NO = "Lagre endringer",
    genDefaultSave_NO = "Lagre",
    settingsDefaultSelectCountryCode_NO = "Velg landskoder",
    genDefaultSelect_NO = "Velg",
    genDefaultSend_NO = "Sende",
    absentDefaultSetPresence_NO = "Still tilstedeværelse",
    settingsDefaultSettings_NO = "Innstillinger",
    callExtensionDefaultSurname_NO = "Etternavn",
    settingsDefaultSwitchboardNumber_NO = "Sentralbord nummer",
    absentDefaultSystemAttOne_NO = "System attributt 1",
    absentDeafaultSystemAttTwo_NO = "System attributt 2",
    absentDeafaultWillReturnSoon_NO = "Er snart tilbake",
    alertDefaultWrongInputTryAgain_NO = "Feil inntasting!Prøv igjen!",
    voiceMailDefaultVoiceMail_NO = "Talepostkassen",
    genDefaultYes_NO = "Ja",

    accessPBXDefault_NO = "Access PBX",
    autoAccessDefault_NO = "Auto tilgang PBX",
    accessViaPINCodeDefault_NO = "Access PBX PIN-kode",
    dialDefault_NO = "Ringe (via PBX)";


    //================= I T A L Y ==============================================

    private String

            settingsDefaultAbout_IT = "info Su Mobisma",
    SettingsDefaultAccessPINcode_IT = "Codice PIN Accesso PBX",
    extensionDefaultAddNew_IT = "Aggiungi",
    callForwardDefaultAllCalls_IT = "Tutte le chiamate",
    absentDefaultAtExt_IT = "A Interno",
    settingsDefaultAutoAccess_IT = "Accesso Auto PBX",
    genDefaultBack_IT = "Indietro",
    absentDefaultBackAt_IT = "Indietro a",
    callForwardDefaultBusy_IT = "Occupato",
    callForwardDefaultBusyNoAnswer_IT = "Occupato/No Risposta",
    callDefaultCall_IT = "Chiamata",
    extensionDefaultCall_IT = "Chiamata Interno",
    callForwardDefault_IT = "Deviazione Chiamata",
    msgDefaultCallistIsEmpty_IT = "Elenco chiamate vuoto",
    genDefaultCancel_IT = "Annulla",
    alertDefaultCantAddAnymoreExt_IT =
            "Non è possibile aggiungere interni, max 25",
    alertDefaultCouldntAddChangesEmtpyField_IT = "Errore! Campo vuoto",
    alertDefaultCouldntAddRecord_IT = "Non è possibile aggiungere record",
    alsertDefaultChangesSave_IT = "\n\n\n...Salvataggio modifiche...",
    mgsDefaultContactListIsEmpty_IT = "Elenco contatti vuoto",
    alertDefaultCountryCodeError_IT = "Errore Prefisso Internazionale!",
    settingsDefaultCountryCode_IT = "Prefisso Internazionale",
    genDefaultDelete_IT = "Cancella",
    genDefaultDeleteAll_IT = "Cancella Tutto",
    dialledCallsDefault_IT = "Chiamate Effettuate",
    callForwardDefaultDontDisturb_IT = "Non Disturbare",
    genDefaultEdit_IT = "Modifica",
    settingsDefaultEditPBXAccess_IT = "Modifica Accesso PBX",
    absentDefaultEditPresence_IT = "Modifica Presenza",
    voiceMailDefaultEditVoicemail_IT = "Modifica Voice Mail",
    enterDefaultEngerCharacter_IT = "Inserire carattere",
    enterDefaultEnterExtension_IT = "Inserire interno",
    enterDefaultEnterGroupNumber_IT = "Inserire numero gruppo",
    enterDefaultEnterHHMM_IT = "Inserire OOMM",
    enterDefaultEnterNumber_IT = "Inserire numero",
    alertDefaultErrorChangeTo_IT = "ERRORE! Modifica in",
    alertDefaultError_IT = "Errore!",
    enterDefaultEnterMMDD_IT = "Inserire MMGG",
    exitDefaultExitTheProgramYesOrNo_IT = "Uscire dal programma Si o No ?",
    genDefaultExit_IT = "Esci",
    settingsDefaultExtension_IT = "Interno",
    callExtensionDefaultExtensionWasAdded_IT = "L'interno è stato aggiunto",
    callForwardDefaultExternCalls_IT = "Chiamate Esterne",
    absentDefaultGoneHome_IT = "Assente",
    groupsDefaultGroups_IT = "Gruppi",
    settingsDefaultHelp_IT = "Aiuto",
    absentDefaultInAMeeting_IT = "In Riunione",
    alertDefaultInfo_IT = "Info!",
    alertDefaultInstedOf_IT = "al posto di",
    callForwardDefaultInternCalls_IT = "Chiamate Interne",
    settingsDefaultLanguage_IT = "Lingua",
    settingsDefaultLineAccess_IT = "Accesso-Linea",
    groupsDefaultLoginAllGroups_IT = "Login tutti i gruppi",
    groupsDefaultLoginSpecificGroup_IT = "Login gruppo",
    groupsDefaultLogoutAllGroups_IT = "Logout tutti i gruppi",
    groupsDefaultLogoutSpecificGroup_IT = "Logout gruppo",
    alertDefaultMaxSize_IT = "Massimizza!",
    genDefaultMinimise_IT = "Minimizza",
    callExtensionDefaultName_IT = "Nome",
    exitDefaultNo_IT = "No",
    callForwardDefaultNoAnswer_IT = "No risposta",
    settingsDefaultOptions_IT = "Opzioni",
    absentDefaultOutUntil_IT = "Assente a",
    absentDefaultPersonalAtt_IT = "Attributo Personale",
    settingsDefaultPINcode_IT = "PIN-code",
    settingsDefaultPreEditCode_IT = "Codice Pre Modifica",
    callForwardDefaultRemove_IT = "Rimuovi",
    absentDefaultRemovePresence_IT = "Rimuovi Presenza",
    exitDefaultRestartProgram_IT = "\n\n\n...Riavvia.... Programma",
    alertDefaultSaveChanges_IT = "Salva Modifiche",
    genDefaultSave_IT = "Salva",
    settingsDefaultSelectCountryCode_IT = "Selezione Prefisso Internazionale",
    genDefaultSelect_IT = "Seleziona",
    genDefaultSend_IT = "Invia",
    absentDefaultSetPresence_IT = "Imposta Presenza",
    settingsDefaultSettings_IT = "Impostazioni",
    callExtensionDefaultSurname_IT = "Cognome",
    settingsDefaultSwitchboardNumber_IT = "Numero principale",
    absentDefaultSystemAttOne_IT = "Attr. Sistema 1",
    absentDeafaultSystemAttTwo_IT = "Attr. Sistema 2",
    absentDeafaultWillReturnSoon_IT = "Torno Subito",
    alertDefaultWrongInputTryAgain_IT = "Errore! Riprovare!",
    voiceMailDefaultVoiceMail_IT = "VoiceMail",
    genDefaultYes_IT = "Si",

    accessPBXDefault_IT = "Accesso PBX",
    autoAccessDefault_IT = "Accesso Auto PBX",
    accessViaPINCodeDefault_IT = "Codice PIN Accesso PBX",
    dialDefault_IT = "Chiamata (via PBX)";


    //==================== S P A N I S H =======================================

    private String

            settingsDefaultAbout_SP = "Acerca de Mobisma",
    SettingsDefaultAccessPINcode_SP = "Acceso PBX-Cód.PIN",
    extensionDefaultAddNew_SP = "Añadir nuevo",
    callForwardDefaultAllCalls_SP = "Todas las llamadas",
    absentDefaultAtExt_SP = "En la Ext",
    settingsDefaultAutoAccess_SP = "Acceso auto. a PBX",
    genDefaultBack_SP = "Volver",
    absentDefaultBackAt_SP = "Vuelvo a las",
    callForwardDefaultBusy_SP = "Ocupado",
    callForwardDefaultBusyNoAnswer_SP = "Ocup/No contesta",
    callDefaultCall_SP = "Llamar",
    extensionDefaultCall_SP = "Llamar a extensión",
    callForwardDefault_SP = "Desvío de Llamada",
    msgDefaultCallistIsEmpty_SP = "Lista de llamadas vacía",
    genDefaultCancel_SP = "Cancelar",
    alertDefaultCantAddAnymoreExt_SP = "No se pueden añadir más ext., Máx.25",
    alertDefaultCouldntAddChangesEmtpyField_SP =
            "No se pueden añadir cambios, campo vacío",
    alertDefaultCouldntAddRecord_SP = "No se puede añadir entrada",
    alertDefaultChangesSave_SP = "\n\n\n...Cambios guardados...",
    mgsDefaultContactListIsEmpty_SP = "Lista de contactos vacía",
    alertDefaultCountryCodeError_SP = "Error de cód. de país",
    settingsDefaultCountryCode_SP = "Código de país",
    genDefaultDelete_SP = "Borrar",
    genDefaultDeleteAll_SP = "Borrar todo",
    dialledCallsDefault_SP = "Llamadas realizadas",
    callForwardDefaultDontDisturb_SP = "No molesten",
    genDefaultEdit_SP = "Editar",
    settingsDefaultEditPBXAccess_SP = "Editar Acceso a PBX",
    absentDefaultEditPresence_SP = "Editar Presencia",
    voiceMailDefaultEditVoicemail_SP = "Editar Búzón",
    enterDefaultEngerCharacter_SP = "Introducir caracter",
    enterDefaultEnterExtension_SP = "Introducir extensión",
    enterDefaultEnterGroupNumber_SP = "Introducir núm. Grupo",
    enterDefaultEnterHHMM_SP = "Introducir HHMM",
    enterDefaultEnterNumber_SP = "Introducir número",
    alertDefaultErrorChangeTo_SP = "ERROR! cambiar a",
    alertDefaultError_SP = "Error !",
    enterDefaultEnterMMDD_SP = "Introducir MMDD",
    exitDefaultExitTheProgramYesOrNo_SP = "Salir del programa SI o NO",
    genDefaultExit_SP = "Salir",
    settingsDefaultExtension_SP = "Extensión",
    callExtensionDefaultExtensionWasAdded_SP = "Extensión añadida",
    callForwardDefaultExternCalls_SP = "Llam. Externas",
    absentDefaultGoneHome_SP = "He ido a casa",
    groupsDefaultGroups_SP = "Grupos",
    settingsDefaultHelp_SP = "Ayuda",
    absentDefaultInAMeeting_SP = "En reunión",
    alertDefaultInfo_SP = "Info!",
    alertDefaultInstedOf_SP = "en lugar de",
    callForwardDefaultInternCalls_SP = "Llam. Internas",
    settingsDefaultLanguage_SP = "Idioma",
    settingsDefaultLineAccess_SP = "Acceso a LN",
    groupsDefaultLoginAllGroups_SP = "Registro todos Grps.",
    groupsDefaultLoginSpecificGroup_SP = "Reg. Grupo específico",
    groupsDefaultLogoutAllGroups_SP = "Baja todos Grps.",
    groupsDefaultLogoutSpecificGroup_SP = "Baja Grupo específico",
    alertDefaultMaxSize_SP = "Tamaño máximo!",
    genDefaultMinimise_SP = "Minimizar",
    callExtensionDefaultName_SP = "Nombre",
    exitDefaultNo_SP = "No",
    callForwardDefaultNoAnswer_SP = "Sin respuesta",
    settingsDefaultOptions_SP = "Opciones",
    absentDefaultOutUntil_SP = "Fuera hasta",
    absentDefaultPersonalAtt_SP = "Caract.personal",
    settingsDefaultPINcode_SP = "Cód. PIN",
    settingsDefaultPreEditCode_SP = "Cód. Pre-edit",
    callForwardDefaultRemove_SP = "Eliminar",
    absentDefaultRemovePresence_SP = "Eliminar Presencia",
    exitDefaultRestartProgram_SP = "\n\n\n...Reinicar programa...",
    alertDefaultSaveChanges_SP = "Guardar cambios",
    genDefaultSave_SP = "Guardar",
    settingsDefaultSelectCountryCode_SP = "Selecc. Código de país",
    genDefaultSelect_SP = "Seleccionar",
    genDefaultSend_SP = "Enviar",
    absentDefaultSetPresence_SP = "Ajustar Presencia",
    settingsDefaultSettings_SP = "Configuración",
    callExtensionDefaultSurname_SP = "Apellido",
    settingsDefaultSwitchboardNumber_SP = "Número centralita",
    absentDefaultSystemAttOne_SP = "Carácter. 1 Sistema",
    absentDeafaultSystemAttTwo_SP = "Carácter. 2 Sistema",
    absentDeafaultWillReturnSoon_SP = "Volveré pronto",
    alertDefaultWrongInputTryAgain_SP =
            "Entrada incorrecta! Intentar nuevamente",
    voiceMailDefaultVoiceMail_SP = "Buzón",
    genDefaultYes_SP = "Sí",

    accessPBXDefault_SP = "Acceso PBX",
    autoAccessDefault_SP = "Acceso auto. a PBX",
    accessViaPINCodeDefault_SP = "Acceso PBX-Cód.PIN",
    dialDefault_SP = "Marcar (vía PBX)";


    //==========================================================================

    // GENERELLA default strängar

    private String
            settingsDefaultEnterpriseLicense_DEF;

    //================== D E F A U L T =========================================


    private String

            settingsDefaultAbout_DEF,
    SettingsDefaultAccessPINcode_DEF,
    extensionDefaultAddNew_DEF,
    callForwardDefaultAllCalls_DEF,
    absentDefaultAtExt_DEF,
    settingsDefaultAutoAccess_DEF,
    genDefaultBack_DEF,
    absentDefaultBackAt_DEF,
    callForwardDefaultBusy_DEF,
    callForwardDefaultBusyNoAnswer_DEF,
    callDefaultCall_DEF,
    extensionDefaultCall_DEF,
    callForwardDefault_DEF,
    msgDefaultCallistIsEmpty_DEF,
    genDefaultCancel_DEF,
    alertDefaultCantAddAnymoreExt_DEF,
    alertDefaultCouldntAddChangesEmtpyField_DEF,
    alertDefaultCouldntAddRecord_DEF,
    alsertDefaultChangesSave_DEF,
    mgsDefaultContactListIsEmpty_DEF,
    alertDefaultCountryCodeError_DEF,
    settingsDefaultCountryCode_DEF,
    genDefaultDelete_DEF,
    genDefaultDeleteAll_DEF,
    dialledCallsDefault_DEF,
    callForwardDefaultDontDisturb_DEF,
    genDefaultEdit_DEF,
    settingsDefaultEditPBXAccess_DEF,
    absentDefaultEditPresence_DEF,
    voiceMailDefaultEditVoicemail_DEF,
    enterDefaultEngerCharacter_DEF,
    enterDefaultEnterExtension_DEF,
    enterDefaultEnterGroupNumber_DEF,
    enterDefaultEnterHHMM_DEF,
    enterDefaultEnterNumber_DEF,
    alertDefaultErrorChangeTo_DEF,
    alertDefaultError_DEF,
    enterDefaultEnterMMDD_DEF,
    exitDefaultExitTheProgramYesOrNo_DEF,
    genDefaultExit_DEF,
    settingsDefaultExtension_DEF,
    callExtensionDefaultExtensionWasAdded_DEF,
    callForwardDefaultExternCalls_DEF,
    absentDefaultGoneHome_DEF,
    groupsDefaultGroups_DEF,
    settingsDefaultHelp_DEF,
    absentDefaultInAMeeting_DEF,
    alertDefaultInfo_DEF,
    alertDefaultInstedOf_DEF,
    callForwardDefaultInternCalls_DEF,
    settingsDefaultLanguage_DEF,
    settingsDefaultLineAccess_DEF,
    groupsDefaultLoginAllGroups_DEF,
    groupsDefaultLoginSpecificGroup_DEF,
    groupsDefaultLogoutAllGroups_DEF,
    groupsDefaultLogoutSpecificGroup_DEF,
    alertDefaultMaxSize_DEF,
    genDefaultMinimise_DEF,
    callExtensionDefaultName_DEF,
    exitDefaultNo_DEF,
    callForwardDefaultNoAnswer_DEF,
    settingsDefaultOptions_DEF,
    absentDefaultOutUntil_DEF,
    absentDefaultPersonalAtt_DEF,
    settingsDefaultPINcode_DEF,
    settingsDefaultPreEditCode_DEF,
    callForwardDefaultRemove_DEF,
    absentDefaultRemovePresence_DEF,
    exitDefaultRestartProgram_DEF,
    alertDefaultSaveChanges_DEF,
    genDefaultSave_DEF,
    settingsDefaultSelectCountryCode_DEF,
    genDefaultSelect_DEF,
    genDefaultSend_DEF,
    absentDefaultSetPresence_DEF,
    settingsDefaultSettings_DEF,
    callExtensionDefaultSurname_DEF,
    settingsDefaultSwitchboardNumber_DEF,
    absentDefaultSystemAttOne_DEF,
    absentDeafaultSystemAttTwo_DEF,
    absentDeafaultWillReturnSoon_DEF,
    alertDefaultWrongInputTryAgain_DEF,
    voiceMailDefaultVoiceMail_DEF,
    genDefaultYes_DEF,

    accessPBXDefault_DEF,
    autoAccessDefault_DEF,
    accessViaPINCodeDefault_DEF,
    dialDefault_DEF;

    //===========================================================================



    public DTMFRequestSony() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException, IOException {

        try {
            addrBook = RecordStore.openRecordStore("TheAddressBook", true);
        } catch (RecordStoreException e) {
            addrBook = null;
        }

        question = new StringItem("Question: ",
                                  "A plane crashes on the border between Canada"
                                  +
                                  "and the US. Where are the survivors buried?");

        this.ResponceMessage = "";
        this.tz = tz;
        today = new Date();
        today.getTime();
        today.toString();
        this.todayDate = today.toString();
        System.out.println(today);

        long time = today.getTime();

        this.antalDagar = 30; // anger hur många dagar programmet ska vara öppet innan det stängs....

        this.wrongmessage = wrongmessage;

        try {
            this.demoLicens = getDemoStatus();
        } catch (RecordStoreNotOpenException ex6) {
        } catch (InvalidRecordIDException ex6) {
        } catch (RecordStoreException ex6) {
        }

        try {
            this.checkStatus_PBX = getPBXStatus();
        } catch (RecordStoreNotOpenException ex6) {
        } catch (InvalidRecordIDException ex6) {
        } catch (RecordStoreException ex6) {
        }

        try {
            this.HGP_PBX = getKeyCode();
        } catch (RecordStoreNotOpenException ex4) {
        } catch (InvalidRecordIDException ex4) {
        } catch (RecordStoreException ex4) {
        }
        try {
            this.accessNumber_PBX = getAccessNumber();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            this.switchBoardNumber_PBX = getSwitchBoardNumber();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        try {
            this.extensionNumber_PBX = getExtensionNumber();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        try {
            this.pinCodeNumber_PBX = getPinCodeNumber();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }

        try {
            this.systemText_1_Attribute = getSystemText_1_Attribute();
        } catch (RecordStoreNotOpenException ex6) {
        } catch (InvalidRecordIDException ex6) {
        } catch (RecordStoreException ex6) {
        }

        try {
            this.systemText_2_Attribute = getSystemText_2_Attribute();
        } catch (RecordStoreNotOpenException ex8) {
        } catch (InvalidRecordIDException ex8) {
        } catch (RecordStoreException ex8) {
        }

        try {
            this.personalText_Attribute = getPersonalText_Attribute();
        } catch (RecordStoreNotOpenException ex10) {
        } catch (InvalidRecordIDException ex10) {
        } catch (RecordStoreException ex10) {
        }

        try {
            this.precode_PBX = getPreEditCode();
        } catch (RecordStoreNotOpenException ex10) {
        } catch (InvalidRecordIDException ex10) {
        } catch (RecordStoreException ex10) {
        }

        try {
            this.voiceMailSwitchboard_PBX = getVoiceEditCode();
        } catch (RecordStoreNotOpenException ex10) {
        } catch (InvalidRecordIDException ex10) {
        } catch (RecordStoreException ex10) {
        }

        this.accessCode = accessCode;
        this.internNumber = internNumber;
        this.accessNumber_PBX = accessNumber_PBX;

        this.pbx_auto = "";
        this.pbx_pincode = "2";
        this.identy = ""; // System.getProperty("com.sonyericsson.imei");
        this.checkIdenty = checkIdenty;

        this.day = day;
        this.month = month;
        setDBDate(); // OBS.. Det här metodanropet ska ligga här efter month och day.
        setDBDateBack();

        this.pbx_auto = pbx_auto;
        this.pbx_pincode = pbx_pincode;
        this.HGP_PBX = HGP_PBX;
        this.systemText_1_Attribute = systemText_1_Attribute;
        this.systemText_2_Attribute = systemText_2_Attribute;
        this.personalText_Attribute = personalText_Attribute;
        this.precode_PBX = precode_PBX;
        this.voiceMailSwitchboard_PBX = voiceMailSwitchboard_PBX;

        openRecStore();

        try {
            this.lang_PBX = getLanguage();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        closeRecStore();

        openRecStore();
        try {
            this.countryCode_PBX = getLanguageCallNumber();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        closeRecStore();

        try {
            upDateDataStore();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            setDataStore();
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreNotOpenException ex2) {
        } catch (RecordStoreException ex2) {
        }

        //============== Generella ord/text i prg ==============================

        settingsDefaultEnterpriseLicense_DEF =
                settingsDefaultEnterpriseLicense_GEN;

        if (this.lang_PBX.equals("0")) { // Danish

            settingsDefaultAbout_DEF = settingsDefaultAbout_DE;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_DE;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_DE;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_DE;
            absentDefaultAtExt_DEF = absentDefaultAtExt_DE;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_DE;
            genDefaultBack_DEF = genDefaultBack_DE;
            absentDefaultBackAt_DEF = absentDefaultBackAt_DE;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_DE;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_DE;
            callDefaultCall_DEF = callDefaultCall_DE;
            extensionDefaultCall_DEF = extensionDefaultCall_DE;
            callForwardDefault_DEF = callForwardDefault_DE;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_DE;
            genDefaultCancel_DEF = genDefaultCancel_DE;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_DE;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_DE;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_DE;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_DE;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_DE;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_DE;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_DE;
            genDefaultDelete_DEF = genDefaultDelete_DE;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_DE;
            dialledCallsDefault_DEF = dialledCallsDefault_DE;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_DE;
            genDefaultEdit_DEF = genDefaultEdit_DE;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_DE;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_DE;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_DE;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_DE;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_DE;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_DE;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_DE;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_DE;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_DE;
            alertDefaultError_DEF = alertDefaultError_DE;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_DE;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_DE;
            genDefaultExit_DEF = genDefaultExit_DE;
            settingsDefaultExtension_DEF = settingsDefaultExtension_DE;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_DE;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_DE;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_DE;
            groupsDefaultGroups_DEF = groupsDefaultGroups_DE;
            settingsDefaultHelp_DEF = settingsDefaultHelp_DE;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_DE;
            alertDefaultInfo_DEF = alertDefaultInfo_DE;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_DE;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_DE;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_DE;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_DE;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_DE;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_DE;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_DE;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_DE;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_DE;
            genDefaultMinimise_DEF = genDefaultMinimise_DE;
            callExtensionDefaultName_DEF = callExtensionDefaultName_DE;
            exitDefaultNo_DEF = exitDefaultNo_DE;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_DE;
            settingsDefaultOptions_DEF = settingsDefaultOptions_DE;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_DE;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_DE;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_DE;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_DE;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_DE;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_DE;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_DE;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_DE;
            genDefaultSave_DEF = genDefaultSave_DE;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_DE;
            genDefaultSelect_DEF = genDefaultSelect_DE;
            genDefaultSend_DEF = genDefaultSend_DE;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_DE;
            settingsDefaultSettings_DEF = settingsDefaultSettings_DE;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_DE;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_DE;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_DE;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_DE;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_DE;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_DE;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_DE;
            genDefaultYes_DEF = genDefaultYes_DE;

            accessPBXDefault_DEF = accessPBXDefault_DE;
            autoAccessDefault_DEF = autoAccessDefault_DE;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_DE;
            dialDefault_DEF = dialDefault_DE;

        }

        if (this.lang_PBX.equals("1")) { // Dutch

            settingsDefaultAbout_DEF = settingsDefaultAbout_DU;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_DU;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_DU;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_DU;
            absentDefaultAtExt_DEF = absentDefaultAtExt_DU;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_DU;
            genDefaultBack_DEF = genDefaultBack_DU;
            absentDefaultBackAt_DEF = absentDefaultBackAt_DU;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_DU;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_DU;
            callDefaultCall_DEF = callDefaultCall_DU;
            extensionDefaultCall_DEF = extensionDefaultCall_DU;
            callForwardDefault_DEF = callForwardDefault_DU;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_DU;
            genDefaultCancel_DEF = genDefaultCancel_DU;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_DU;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_DU;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_DU;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_DU;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_DU;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_DU;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_DU;
            genDefaultDelete_DEF = genDefaultDelete_DU;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_DU;
            dialledCallsDefault_DEF = dialledCallsDefault_DU;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_DU;
            genDefaultEdit_DEF = genDefaultEdit_DU;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_DU;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_DU;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_DU;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_DU;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_DU;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_DU;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_DU;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_DU;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_DU;
            alertDefaultError_DEF = alertDefaultError_DU;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_DU;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_DU;
            genDefaultExit_DEF = genDefaultExit_DU;
            settingsDefaultExtension_DEF = settingsDefaultExtension_DU;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_DU;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_DU;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_DU;
            groupsDefaultGroups_DEF = groupsDefaultGroups_DU;
            settingsDefaultHelp_DEF = settingsDefaultHelp_DU;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_DU;
            alertDefaultInfo_DEF = alertDefaultInfo_DU;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_DU;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_DU;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_DU;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_DU;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_DU;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_DU;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_DU;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_DU;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_DU;
            genDefaultMinimise_DEF = genDefaultMinimise_DU;
            callExtensionDefaultName_DEF = callExtensionDefaultName_DU;
            exitDefaultNo_DEF = exitDefaultNo_DU;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_DU;
            settingsDefaultOptions_DEF = settingsDefaultOptions_DU;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_DU;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_DU;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_DU;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_DU;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_DU;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_DU;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_DU;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_DU;
            genDefaultSave_DEF = genDefaultSave_DU;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_DU;
            genDefaultSelect_DEF = genDefaultSelect_DU;
            genDefaultSend_DEF = genDefaultSend_DU;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_DU;
            settingsDefaultSettings_DEF = settingsDefaultSettings_DU;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_DU;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_DU;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_DU;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_DU;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_DU;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_DU;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_DU;
            genDefaultYes_DEF = genDefaultYes_DU;

            accessPBXDefault_DEF = accessPBXDefault_DU;
            autoAccessDefault_DEF = autoAccessDefault_DU;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_DU;
            dialDefault_DEF = dialDefault_DU;

        }

        if (this.lang_PBX.equals("2")) { // English

            settingsDefaultAbout_DEF = settingsDefaultAbout_ENG;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_ENG;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_ENG;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_ENG;
            absentDefaultAtExt_DEF = absentDefaultAtExt_ENG;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_ENG;
            genDefaultBack_DEF = genDefaultBack_ENG;
            absentDefaultBackAt_DEF = absentDefaultBackAt_ENG;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_ENG;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_ENG;
            callDefaultCall_DEF = callDefaultCall_ENG;
            extensionDefaultCall_DEF = extensionDefaultCall_ENG;
            callForwardDefault_DEF = callForwardDefault_ENG;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_ENG;
            genDefaultCancel_DEF = genDefaultCancel_ENG;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_ENG;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_ENG;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_ENG;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_ENG;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_ENG;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_ENG;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_ENG;
            genDefaultDelete_DEF = genDefaultDelete_ENG;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_ENG;
            dialledCallsDefault_DEF = dialledCallsDefault_ENG;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_ENG;
            genDefaultEdit_DEF = genDefaultEdit_ENG;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_ENG;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_ENG;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_ENG;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_ENG;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_ENG;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_ENG;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_ENG;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_ENG;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_ENG;
            alertDefaultError_DEF = alertDefaultError_ENG;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_ENG;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_ENG;
            genDefaultExit_DEF = genDefaultExit_ENG;
            settingsDefaultExtension_DEF = settingsDefaultExtension_ENG;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_ENG;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_ENG;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_ENG;
            groupsDefaultGroups_DEF = groupsDefaultGroups_ENG;
            settingsDefaultHelp_DEF = settingsDefaultHelp_ENG;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_ENG;
            alertDefaultInfo_DEF = alertDefaultInfo_ENG;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_ENG;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_ENG;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_ENG;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_ENG;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_ENG;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_ENG;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_ENG;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_ENG;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_ENG;
            genDefaultMinimise_DEF = genDefaultMinimise_ENG;
            callExtensionDefaultName_DEF = callExtensionDefaultName_ENG;
            exitDefaultNo_DEF = exitDefaultNo_ENG;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_ENG;
            settingsDefaultOptions_DEF = settingsDefaultOptions_ENG;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_ENG;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_ENG;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_ENG;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_ENG;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_ENG;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_ENG;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_ENG;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_ENG;
            genDefaultSave_DEF = genDefaultSave_ENG;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_ENG;
            genDefaultSelect_DEF = genDefaultSelect_ENG;
            genDefaultSend_DEF = genDefaultSend_ENG;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_ENG;
            settingsDefaultSettings_DEF = settingsDefaultSettings_ENG;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_ENG;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_ENG;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_ENG;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_ENG;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_ENG;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_ENG;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_ENG;
            genDefaultYes_DEF = genDefaultYes_ENG;

            accessPBXDefault_DEF = accessPBXDefault_ENG;
            autoAccessDefault_DEF = autoAccessDefault_ENG;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_ENG;
            dialDefault_DEF = dialDefault_ENG;

        }

        if (this.lang_PBX.equals("3")) { // Finnish

            settingsDefaultAbout_DEF = settingsDefaultAbout_FIN;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_FIN;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_FIN;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_FIN;
            absentDefaultAtExt_DEF = absentDefaultAtExt_FIN;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_FIN;
            genDefaultBack_DEF = genDefaultBack_FIN;
            absentDefaultBackAt_DEF = absentDefaultBackAt_FIN;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_FIN;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_FIN;
            callDefaultCall_DEF = callDefaultCall_FIN;
            extensionDefaultCall_DEF = extensionDefaultCall_FIN;
            callForwardDefault_DEF = callForwardDefault_FIN;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_FIN;
            genDefaultCancel_DEF = genDefaultCancel_FIN;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_FIN;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_FIN;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_FIN;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_FIN;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_FIN;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_FIN;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_FIN;
            genDefaultDelete_DEF = genDefaultDelete_FIN;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_FIN;
            dialledCallsDefault_DEF = dialledCallsDefault_FIN;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_FIN;
            genDefaultEdit_DEF = genDefaultEdit_FIN;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_FIN;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_FIN;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_FIN;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_FIN;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_FIN;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_FIN;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_FIN;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_FIN;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_FIN;
            alertDefaultError_DEF = alertDefaultError_FIN;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_FIN;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_FIN;
            genDefaultExit_DEF = genDefaultExit_FIN;
            settingsDefaultExtension_DEF = settingsDefaultExtension_FIN;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_FIN;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_FIN;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_FIN;
            groupsDefaultGroups_DEF = groupsDefaultGroups_FIN;
            settingsDefaultHelp_DEF = settingsDefaultHelp_FIN;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_FIN;
            alertDefaultInfo_DEF = alertDefaultInfo_FIN;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_FIN;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_FIN;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_FIN;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_FIN;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_FIN;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_FIN;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_FIN;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_FIN;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_FIN;
            genDefaultMinimise_DEF = genDefaultMinimise_FIN;
            callExtensionDefaultName_DEF = callExtensionDefaultName_FIN;
            exitDefaultNo_DEF = exitDefaultNo_FIN;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_FIN;
            settingsDefaultOptions_DEF = settingsDefaultOptions_FIN;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_FIN;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_FIN;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_FIN;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_FIN;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_FIN;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_FIN;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_FIN;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_FIN;
            genDefaultSave_DEF = genDefaultSave_FIN;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_FIN;
            genDefaultSelect_DEF = genDefaultSelect_FIN;
            genDefaultSend_DEF = genDefaultSend_FIN;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_FIN;
            settingsDefaultSettings_DEF = settingsDefaultSettings_FIN;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_FIN;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_FIN;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_FIN;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_FIN;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_FIN;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_FIN;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_FIN;
            genDefaultYes_DEF = genDefaultYes_FIN;

            accessPBXDefault_DEF = accessPBXDefault_FIN;
            autoAccessDefault_DEF = autoAccessDefault_FIN;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_FIN;
            dialDefault_DEF = dialDefault_FIN;

        }

        if (this.lang_PBX.equals("4")) { // French

            settingsDefaultAbout_DEF = settingsDefaultAbout_FRA;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_FRA;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_FRA;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_FRA;
            absentDefaultAtExt_DEF = absentDefaultAtExt_FRA;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_FRA;
            genDefaultBack_DEF = genDefaultBack_FRA;
            absentDefaultBackAt_DEF = absentDefaultBackAt_FRA;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_FRA;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_FRA;
            callDefaultCall_DEF = callDefaultCall_FRA;
            extensionDefaultCall_DEF = extensionDefaultCall_FRA;
            callForwardDefault_DEF = callForwardDefault_FRA;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_FRA;
            genDefaultCancel_DEF = genDefaultCancel_FRA;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_FRA;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_FRA;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_FRA;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_FRA;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_FRA;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_FRA;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_FRA;
            genDefaultDelete_DEF = genDefaultDelete_FRA;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_FRA;
            dialledCallsDefault_DEF = dialledCallsDefault_FRA;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_FRA;
            genDefaultEdit_DEF = genDefaultEdit_FRA;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_FRA;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_FRA;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_FRA;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_FRA;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_FRA;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_FRA;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_FRA;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_FRA;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_FRA;
            alertDefaultError_DEF = alertDefaultError_FRA;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_FRA;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_FRA;
            genDefaultExit_DEF = genDefaultExit_FRA;
            settingsDefaultExtension_DEF = settingsDefaultExtension_FRA;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_FRA;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_FRA;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_FRA;
            groupsDefaultGroups_DEF = groupsDefaultGroups_FRA;
            settingsDefaultHelp_DEF = settingsDefaultHelp_FRA;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_FRA;
            alertDefaultInfo_DEF = alertDefaultInfo_FRA;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_FRA;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_FRA;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_FRA;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_FRA;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_FRA;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_FRA;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_FRA;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_FRA;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_FRA;
            genDefaultMinimise_DEF = genDefaultMinimise_FRA;
            callExtensionDefaultName_DEF = callExtensionDefaultName_FRA;
            exitDefaultNo_DEF = exitDefaultNo_FRA;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_FRA;
            settingsDefaultOptions_DEF = settingsDefaultOptions_FRA;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_FRA;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_FRA;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_FRA;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_FRA;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_FRA;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_FRA;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_FRA;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_FRA;
            genDefaultSave_DEF = genDefaultSave_FRA;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_FRA;
            genDefaultSelect_DEF = genDefaultSelect_FRA;
            genDefaultSend_DEF = genDefaultSend_FRA;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_FRA;
            settingsDefaultSettings_DEF = settingsDefaultSettings_FRA;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_FRA;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_FRA;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_FRA;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_FRA;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_FRA;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_FRA;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_FRA;
            genDefaultYes_DEF = genDefaultYes_FRA;

            accessPBXDefault_DEF = accessPBXDefault_FRA;
            autoAccessDefault_DEF = autoAccessDefault_FRA;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_FRA;
            dialDefault_DEF = dialDefault_FRA;

        }

        if (this.lang_PBX.equals("5")) { // German

            settingsDefaultAbout_DEF = settingsDefaultAbout_GE;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_GE;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_GE;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_GE;
            absentDefaultAtExt_DEF = absentDefaultAtExt_GE;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_GE;
            genDefaultBack_DEF = genDefaultBack_GE;
            absentDefaultBackAt_DEF = absentDefaultBackAt_GE;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_GE;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_GE;
            callDefaultCall_DEF = callDefaultCall_GE;
            extensionDefaultCall_DEF = extensionDefaultCall_GE;
            callForwardDefault_DEF = callForwardDefault_GE;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_GE;
            genDefaultCancel_DEF = genDefaultCancel_GE;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_GE;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_GE;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_GE;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_GE;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_GE;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_GE;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_GE;
            genDefaultDelete_DEF = genDefaultDelete_GE;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_GE;
            dialledCallsDefault_DEF = dialledCallsDefault_GE;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_GE;
            genDefaultEdit_DEF = genDefaultEdit_GE;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_GE;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_GE;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_GE;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_GE;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_GE;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_GE;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_GE;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_GE;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_GE;
            alertDefaultError_DEF = alertDefaultError_GE;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_GE;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_GE;
            genDefaultExit_DEF = genDefaultExit_GE;
            settingsDefaultExtension_DEF = settingsDefaultExtension_GE;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_GE;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_GE;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_GE;
            groupsDefaultGroups_DEF = groupsDefaultGroups_GE;
            settingsDefaultHelp_DEF = settingsDefaultHelp_GE;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_GE;
            alertDefaultInfo_DEF = alertDefaultInfo_GE;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_GE;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_GE;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_GE;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_GE;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_GE;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_GE;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_GE;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_GE;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_GE;
            genDefaultMinimise_DEF = genDefaultMinimise_GE;
            callExtensionDefaultName_DEF = callExtensionDefaultName_GE;
            exitDefaultNo_DEF = exitDefaultNo_GE;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_GE;
            settingsDefaultOptions_DEF = settingsDefaultOptions_GE;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_GE;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_GE;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_GE;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_GE;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_GE;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_GE;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_GE;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_GE;
            genDefaultSave_DEF = genDefaultSave_GE;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_GE;
            genDefaultSelect_DEF = genDefaultSelect_GE;
            genDefaultSend_DEF = genDefaultSend_GE;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_GE;
            settingsDefaultSettings_DEF = settingsDefaultSettings_GE;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_GE;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_GE;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_GE;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_GE;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_GE;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_GE;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_GE;
            genDefaultYes_DEF = genDefaultYes_GE;

            accessPBXDefault_DEF = accessPBXDefault_GE;
            autoAccessDefault_DEF = autoAccessDefault_GE;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_GE;
            dialDefault_DEF = dialDefault_GE;

        }

        if (this.lang_PBX.equals("6")) { // Norwegian

            settingsDefaultAbout_DEF = settingsDefaultAbout_NO;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_NO;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_NO;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_NO;
            absentDefaultAtExt_DEF = absentDefaultAtExt_NO;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_NO;
            genDefaultBack_DEF = genDefaultBack_NO;
            absentDefaultBackAt_DEF = absentDefaultBackAt_NO;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_NO;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_NO;
            callDefaultCall_DEF = callDefaultCall_NO;
            extensionDefaultCall_DEF = extensionDefaultCall_NO;
            callForwardDefault_DEF = callForwardDefault_NO;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_NO;
            genDefaultCancel_DEF = genDefaultCancel_NO;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_NO;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_NO;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_NO;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_NO;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_NO;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_NO;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_NO;
            genDefaultDelete_DEF = genDefaultDelete_NO;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_NO;
            dialledCallsDefault_DEF = dialledCallsDefault_NO;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_NO;
            genDefaultEdit_DEF = genDefaultEdit_NO;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_NO;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_NO;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_NO;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_NO;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_NO;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_NO;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_NO;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_NO;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_NO;
            alertDefaultError_DEF = alertDefaultError_NO;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_NO;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_NO;
            genDefaultExit_DEF = genDefaultExit_NO;
            settingsDefaultExtension_DEF = settingsDefaultExtension_NO;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_NO;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_NO;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_NO;
            groupsDefaultGroups_DEF = groupsDefaultGroups_NO;
            settingsDefaultHelp_DEF = settingsDefaultHelp_NO;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_NO;
            alertDefaultInfo_DEF = alertDefaultInfo_NO;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_NO;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_NO;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_NO;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_NO;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_NO;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_NO;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_NO;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_NO;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_NO;
            genDefaultMinimise_DEF = genDefaultMinimise_NO;
            callExtensionDefaultName_DEF = callExtensionDefaultName_NO;
            exitDefaultNo_DEF = exitDefaultNo_NO;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_NO;
            settingsDefaultOptions_DEF = settingsDefaultOptions_NO;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_NO;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_NO;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_NO;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_NO;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_NO;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_NO;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_NO;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_NO;
            genDefaultSave_DEF = genDefaultSave_NO;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_NO;
            genDefaultSelect_DEF = genDefaultSelect_NO;
            genDefaultSend_DEF = genDefaultSend_NO;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_NO;
            settingsDefaultSettings_DEF = settingsDefaultSettings_NO;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_NO;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_NO;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_NO;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_NO;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_NO;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_NO;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_NO;
            genDefaultYes_DEF = genDefaultYes_NO;

            accessPBXDefault_DEF = accessPBXDefault_NO;
            autoAccessDefault_DEF = autoAccessDefault_NO;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_NO;
            dialDefault_DEF = dialDefault_NO;

        }

        if (this.lang_PBX.equals("7")) { // Italian

            settingsDefaultAbout_DEF = settingsDefaultAbout_IT;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_IT;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_IT;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_IT;
            absentDefaultAtExt_DEF = absentDefaultAtExt_IT;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_IT;
            genDefaultBack_DEF = genDefaultBack_IT;
            absentDefaultBackAt_DEF = absentDefaultBackAt_IT;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_IT;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_IT;
            callDefaultCall_DEF = callDefaultCall_IT;
            extensionDefaultCall_DEF = extensionDefaultCall_IT;
            callForwardDefault_DEF = callForwardDefault_IT;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_IT;
            genDefaultCancel_DEF = genDefaultCancel_IT;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_IT;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_IT;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_IT;
            alsertDefaultChangesSave_DEF = alsertDefaultChangesSave_IT;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_IT;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_IT;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_IT;
            genDefaultDelete_DEF = genDefaultDelete_IT;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_IT;
            dialledCallsDefault_DEF = dialledCallsDefault_IT;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_IT;
            genDefaultEdit_DEF = genDefaultEdit_IT;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_IT;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_IT;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_IT;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_IT;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_IT;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_IT;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_IT;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_IT;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_IT;
            alertDefaultError_DEF = alertDefaultError_IT;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_IT;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_IT;
            genDefaultExit_DEF = genDefaultExit_IT;
            settingsDefaultExtension_DEF = settingsDefaultExtension_IT;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_IT;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_IT;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_IT;
            groupsDefaultGroups_DEF = groupsDefaultGroups_IT;
            settingsDefaultHelp_DEF = settingsDefaultHelp_IT;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_IT;
            alertDefaultInfo_DEF = alertDefaultInfo_IT;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_IT;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_IT;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_IT;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_IT;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_IT;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_IT;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_IT;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_IT;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_IT;
            genDefaultMinimise_DEF = genDefaultMinimise_IT;
            callExtensionDefaultName_DEF = callExtensionDefaultName_IT;
            exitDefaultNo_DEF = exitDefaultNo_IT;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_IT;
            settingsDefaultOptions_DEF = settingsDefaultOptions_IT;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_IT;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_IT;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_IT;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_IT;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_IT;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_IT;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_IT;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_IT;
            genDefaultSave_DEF = genDefaultSave_IT;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_IT;
            genDefaultSelect_DEF = genDefaultSelect_IT;
            genDefaultSend_DEF = genDefaultSend_IT;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_IT;
            settingsDefaultSettings_DEF = settingsDefaultSettings_IT;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_IT;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_IT;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_IT;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_IT;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_IT;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_IT;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_IT;
            genDefaultYes_DEF = genDefaultYes_IT;

            accessPBXDefault_DEF = accessPBXDefault_IT;
            autoAccessDefault_DEF = autoAccessDefault_IT;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_IT;
            dialDefault_DEF = dialDefault_IT;

        }

        if (this.lang_PBX.equals("8")) { // Spanish

            settingsDefaultAbout_DEF = settingsDefaultAbout_SP;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_SP;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_SP;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_SP;
            absentDefaultAtExt_DEF = absentDefaultAtExt_SP;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_SP;
            genDefaultBack_DEF = genDefaultBack_SP;
            absentDefaultBackAt_DEF = absentDefaultBackAt_SP;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_SP;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_SP;
            callDefaultCall_DEF = callDefaultCall_SP;
            extensionDefaultCall_DEF = extensionDefaultCall_SP;
            callForwardDefault_DEF = callForwardDefault_SP;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_SP;
            genDefaultCancel_DEF = genDefaultCancel_SP;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_SP;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_SP;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_SP;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_SP;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_SP;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_SP;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_SP;
            genDefaultDelete_DEF = genDefaultDelete_SP;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_SP;
            dialledCallsDefault_DEF = dialledCallsDefault_SP;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_SP;
            genDefaultEdit_DEF = genDefaultEdit_SP;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_SP;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_SP;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_SP;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_SP;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_SP;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_SP;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_SP;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_SP;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_SP;
            alertDefaultError_DEF = alertDefaultError_SP;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_SP;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_SP;
            genDefaultExit_DEF = genDefaultExit_SP;
            settingsDefaultExtension_DEF = settingsDefaultExtension_SP;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_SP;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_SP;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_SP;
            groupsDefaultGroups_DEF = groupsDefaultGroups_SP;
            settingsDefaultHelp_DEF = settingsDefaultHelp_SP;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_SP;
            alertDefaultInfo_DEF = alertDefaultInfo_SP;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_SP;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_SP;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_SP;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_SP;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_SP;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_SP;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_SP;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_SP;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_SP;
            genDefaultMinimise_DEF = genDefaultMinimise_SP;
            callExtensionDefaultName_DEF = callExtensionDefaultName_SP;
            exitDefaultNo_DEF = exitDefaultNo_SP;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_SP;
            settingsDefaultOptions_DEF = settingsDefaultOptions_SP;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_SP;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_SP;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_IT;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_SP;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_SP;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_SP;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_SP;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_SP;
            genDefaultSave_DEF = genDefaultSave_SP;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_SP;
            genDefaultSelect_DEF = genDefaultSelect_SP;
            genDefaultSend_DEF = genDefaultSend_SP;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_SP;
            settingsDefaultSettings_DEF = settingsDefaultSettings_SP;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_SP;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_SP;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_SP;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_SP;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_SP;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_SP;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_SP;
            genDefaultYes_DEF = genDefaultYes_SP;

            accessPBXDefault_DEF = accessPBXDefault_SP;
            autoAccessDefault_DEF = autoAccessDefault_SP;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_SP;
            dialDefault_DEF = dialDefault_SP;

        }

        if (this.lang_PBX.equals("9")) { // Swedish

            settingsDefaultAbout_DEF = settingsDefaultAbout_SE;
            SettingsDefaultAccessPINcode_DEF = SettingsDefaultAccessPINcode_SE;
            extensionDefaultAddNew_DEF = extensionDefaultAddNew_SE;
            callForwardDefaultAllCalls_DEF = callForwardDefaultAllCalls_SE;
            absentDefaultAtExt_DEF = absentDefaultAtExt_SE;
            settingsDefaultAutoAccess_DEF = settingsDefaultAutoAccess_SE;
            genDefaultBack_DEF = genDefaultBack_SE;
            absentDefaultBackAt_DEF = absentDefaultBackAt_SE;
            callForwardDefaultBusy_DEF = callForwardDefaultBusy_SE;
            callForwardDefaultBusyNoAnswer_DEF =
                    callForwardDefaultBusyNoAnswer_SE;
            callDefaultCall_DEF = callDefaultCall_SE;
            extensionDefaultCall_DEF = extensionDefaultCall_SE;
            callForwardDefault_DEF = callForwardDefault_SE;
            msgDefaultCallistIsEmpty_DEF = msgDefaultCallistIsEmpty_SE;
            genDefaultCancel_DEF = genDefaultCancel_SE;
            alertDefaultCantAddAnymoreExt_DEF =
                    alertDefaultCantAddAnymoreExt_SE;
            alertDefaultCouldntAddChangesEmtpyField_DEF =
                    alertDefaultCouldntAddChangesEmtpyField_SE;
            alertDefaultCouldntAddRecord_DEF = alertDefaultCouldntAddRecord_SE;
            alsertDefaultChangesSave_DEF = alertDefaultChangesSave_SE;
            mgsDefaultContactListIsEmpty_DEF = mgsDefaultContactListIsEmpty_SE;
            alertDefaultCountryCodeError_DEF = alertDefaultCountryCodeError_SE;
            settingsDefaultCountryCode_DEF = settingsDefaultCountryCode_SE;
            genDefaultDelete_DEF = genDefaultDelete_SE;
            genDefaultDeleteAll_DEF = genDefaultDeleteAll_SE;
            dialledCallsDefault_DEF = dialledCallsDefault_SE;
            callForwardDefaultDontDisturb_DEF =
                    callForwardDefaultDontDisturb_SE;
            genDefaultEdit_DEF = genDefaultEdit_SE;
            settingsDefaultEditPBXAccess_DEF = settingsDefaultEditPBXAccess_SE;
            absentDefaultEditPresence_DEF = absentDefaultEditPresence_SE;
            voiceMailDefaultEditVoicemail_DEF =
                    voiceMailDefaultEditVoicemail_SE;
            enterDefaultEngerCharacter_DEF = enterDefaultEngerCharacter_SE;
            enterDefaultEnterExtension_DEF = enterDefaultEnterExtension_SE;
            enterDefaultEnterGroupNumber_DEF = enterDefaultEnterGroupNumber_SE;
            enterDefaultEnterHHMM_DEF = enterDefaultEnterHHMM_SE;
            enterDefaultEnterNumber_DEF = enterDefaultEnterNumber_SE;
            alertDefaultErrorChangeTo_DEF = alertDefaultErrorChangeTo_SE;
            alertDefaultError_DEF = alertDefaultError_SE;
            enterDefaultEnterMMDD_DEF = enterDefaultEnterMMDD_SE;
            exitDefaultExitTheProgramYesOrNo_DEF =
                    exitDefaultExitTheProgramYesOrNo_SE;
            genDefaultExit_DEF = genDefaultExit_SE;
            settingsDefaultExtension_DEF = settingsDefaultExtension_SE;
            callExtensionDefaultExtensionWasAdded_DEF =
                    callExtensionDefaultExtensionWasAdded_SE;
            callForwardDefaultExternCalls_DEF =
                    callForwardDefaultExternCalls_SE;
            absentDefaultGoneHome_DEF = absentDefaultGoneHome_SE;
            groupsDefaultGroups_DEF = groupsDefaultGroups_SE;
            settingsDefaultHelp_DEF = settingsDefaultHelp_SE;
            absentDefaultInAMeeting_DEF = absentDefaultInAMeeting_SE;
            alertDefaultInfo_DEF = alertDefaultInfo_SE;
            alertDefaultInstedOf_DEF = alertDefaultInstedOf_SE;
            callForwardDefaultInternCalls_DEF =
                    callForwardDefaultInternCalls_SE;
            settingsDefaultLanguage_DEF = settingsDefaultLanguage_SE;
            settingsDefaultLineAccess_DEF = settingsDefaultLineAccess_SE;
            groupsDefaultLoginAllGroups_DEF = groupsDefaultLoginAllGroups_SE;
            groupsDefaultLoginSpecificGroup_DEF =
                    groupsDefaultLoginSpecificGroup_SE;
            groupsDefaultLogoutAllGroups_DEF = groupsDefaultLogoutAllGroups_SE;
            groupsDefaultLogoutSpecificGroup_DEF =
                    groupsDefaultLogoutSpecificGroup_SE;
            alertDefaultMaxSize_DEF = alertDefaultMaxSize_SE;
            genDefaultMinimise_DEF = genDefaultMinimise_SE;
            callExtensionDefaultName_DEF = callExtensionDefaultName_SE;
            exitDefaultNo_DEF = exitDefaultNo_SE;
            callForwardDefaultNoAnswer_DEF = callForwardDefaultNoAnswer_SE;
            settingsDefaultOptions_DEF = settingsDefaultOptions_SE;
            absentDefaultOutUntil_DEF = absentDefaultOutUntil_SE;
            absentDefaultPersonalAtt_DEF = absentDefaultPersonalAtt_SE;
            settingsDefaultPINcode_DEF = settingsDefaultPINcode_SE;
            settingsDefaultPreEditCode_DEF = settingsDefaultPreEditCode_SE;
            callForwardDefaultRemove_DEF = callForwardDefaultRemove_SE;
            absentDefaultRemovePresence_DEF = absentDefaultRemovePresence_SE;
            exitDefaultRestartProgram_DEF = exitDefaultRestartProgram_SE;
            alertDefaultSaveChanges_DEF = alertDefaultSaveChanges_SE;
            genDefaultSave_DEF = genDefaultSave_SE;
            settingsDefaultSelectCountryCode_DEF =
                    settingsDefaultSelectCountryCode_SE;
            genDefaultSelect_DEF = genDefaultSelect_SE;
            genDefaultSend_DEF = genDefaultSend_SE;
            absentDefaultSetPresence_DEF = absentDefaultSetPresence_SE;
            settingsDefaultSettings_DEF = settingsDefaultSettings_SE;
            callExtensionDefaultSurname_DEF = callExtensionDefaultSurname_SE;
            settingsDefaultSwitchboardNumber_DEF =
                    settingsDefaultSwitchboardNumber_SE;
            absentDefaultSystemAttOne_DEF = absentDefaultSystemAttOne_SE;
            absentDeafaultSystemAttTwo_DEF = absentDeafaultSystemAttTwo_SE;
            absentDeafaultWillReturnSoon_DEF = absentDeafaultWillReturnSoon_SE;
            alertDefaultWrongInputTryAgain_DEF =
                    alertDefaultWrongInputTryAgain_SE;
            voiceMailDefaultVoiceMail_DEF = voiceMailDefaultVoiceMail_SE;
            genDefaultYes_DEF = genDefaultYes_SE;

            accessPBXDefault_DEF = accessPBXDefault_SE;
            autoAccessDefault_DEF = autoAccessDefault_SE;
            accessViaPINCodeDefault_DEF = accessViaPINCodeDefault_SE;
            dialDefault_DEF = dialDefault_SE;

        }

        //------------- MainList -----------------------------------------------

        mainList = new List("", Choice.IMPLICIT); // skapar en lista
        mainList.setTitle(null);
        mainTicker = new Ticker("mobismaME");
        mainList.setTicker(mainTicker);

        propertiesCommand = new Command(genDefaultEdit_DEF, Command.OK, 1);
        aboutMobismaCommand = new Command(settingsDefaultAbout_DEF, Command.OK,
                                          2);
        ExitCommandMainList = new Command(genDefaultExit_DEF, Command.OK, 3);

        try {

            Image imageMexon = Image.createImage("/prg_icon/on24.png");
            Image imageMexoff = Image.createImage("/prg_icon/off24.png");
            Image image5a = Image.createImage("/prg_icon/hanvisa24.png");
            Image image6a = Image.createImage("/prg_icon/vidarekoppling24.png");
            Image image7a = Image.createImage("/prg_icon/rostbrevlada24.png");
            Image image8a = Image.createImage("/prg_icon/konf24.png");
            Image image10a = Image.createImage("/prg_icon/minimera24.png");

            mainList.append(absentDefaultSetPresence_DEF, image5a);
            mainList.append(callForwardDefault_DEF, image6a);
            mainList.append(voiceMailDefaultVoiceMail_DEF, image7a);
            mainList.append(groupsDefaultGroups_DEF, image8a);
            mainList.append("Mex on", imageMexon);
            mainList.append("Mex off", imageMexoff);
            mainList.append(genDefaultMinimise_DEF, image10a);

            mainList.addCommand(ExitCommandMainList);
            mainList.addCommand(propertiesCommand);
            mainList.addCommand(aboutMobismaCommand);
            mainList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //---------------- Responce TextBox ---------------------------------------

        response_textbox = new TextBox("Debug view", "", 1024, TextField.ANY);

        responseUpdateCommand = new Command("Update", Command.OK, 1);
        responseDebugOnCommand = new Command("Debug On", Command.OK, 2);
        responseDebugOffCommand = new Command("Debug Off", Command.OK, 3);
        clearCommand = new Command("Clean up", Command.OK, 4);
        responseCancelCommand = new Command("Cancel", Command.BACK, 6);

        response_textbox.addCommand(responseDebugOffCommand);
        response_textbox.addCommand(responseDebugOnCommand);
        response_textbox.addCommand(clearCommand);
        response_textbox.addCommand(responseCancelCommand);
        response_textbox.addCommand(responseUpdateCommand);
        response_textbox.setCommandListener(this);

        //------------- GroupList ----------------------------------------------

        groupList = new List("", Choice.IMPLICIT); // skapar en lista
        groupList.setTitle(null);
        groupTicker = new Ticker(groupsDefaultGroups_DEF);
        groupList.setTicker(groupTicker);

        groupBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 1);

        try {

            Image imageGroup = Image.createImage("/prg_icon/konf24.png");

            groupList.append(groupsDefaultLoginAllGroups_DEF, imageGroup);
            groupList.append(groupsDefaultLogoutAllGroups_DEF, imageGroup);
            groupList.append(groupsDefaultLoginSpecificGroup_DEF, imageGroup);
            groupList.append(groupsDefaultLogoutSpecificGroup_DEF, imageGroup);

            groupList.addCommand(groupBackCommand);
            groupList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //------------- EditSystemAbsentList -----------------------------------

        editSystemAbsentList = new List(absentDefaultEditPresence_DEF,
                                        Choice.IMPLICIT); // skapar en lista

        backEditAbsentSystemCommand = new Command(genDefaultBack_DEF,
                                                  Command.BACK, 1);

        editSystemAbsentList.append(absentDefaultSystemAttOne_DEF, null);
        editSystemAbsentList.append(absentDeafaultSystemAttTwo_DEF, null);
        editSystemAbsentList.append(absentDefaultPersonalAtt_DEF, null);

        editSystemAbsentList.addCommand(backEditAbsentSystemCommand);
        editSystemAbsentList.setCommandListener(this);

        // ------------ keycodeForm --------------------------------------------

        keyCodeForm = new Form("Key Code");
        keyCodeTextField = new TextField("Enter Code", "", 3, TextField.NUMERIC);
        keyCodeSaveCommand = new Command("Save", Command.OK, 1);
        keyCodeCancelCommand = new Command("Cancel", Command.CANCEL, 2);

        keyCodeForm.addCommand(keyCodeSaveCommand);
        keyCodeForm.addCommand(keyCodeCancelCommand);
        keyCodeForm.setCommandListener(this);

        //------------- debugForm ----------------------------------------------

        debugForm = new Form("System");
        debugTextField = new TextField("Enter Code", "", 8,
                                       TextField.PHONENUMBER);
        debugOKCommand = new Command("Log In", Command.OK, 1);
        debugCancelCommand = new Command("Cancel", Command.CANCEL, 2);

        debugForm.addCommand(debugCancelCommand);
        debugForm.addCommand(debugOKCommand);
        debugForm.setCommandListener(this);

        //------------- AllForwardTextBox --------------------------------------

        // Alla samtal textbox


        allCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        allCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        allCallingForwardTicker = new Ticker(callForwardDefaultAllCalls_DEF);
        allCallingForwardForm.setTicker(allCallingForwardTicker);

        allCallForwardDialCommand = new Command(genDefaultSend_DEF, Command.OK,
                                                1);
        allCallForwardBackCommand = new Command(genDefaultBack_DEF,
                                                Command.BACK, 2);

        allCallingForwardForm.addCommand(allCallForwardDialCommand);
        allCallingForwardForm.addCommand(allCallForwardBackCommand);
        allCallingForwardForm.setCommandListener(this);

        // Upptagen


        allBusyCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        allBusyCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        allBusyCallingForwardTicker = new Ticker(callForwardDefaultBusy_DEF);
        allBusyCallingForwardForm.setTicker(allBusyCallingForwardTicker);

        allBusyCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK, 1);
        allBusyCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        allBusyCallingForwardForm.addCommand(allBusyCallForwardDialCommand);
        allBusyCallingForwardForm.addCommand(allBusyCallForwardBackCommand);
        allBusyCallingForwardForm.setCommandListener(this);

        // Inget svar


        allNoAnswerCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        allNoAnswerCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        allNoAnswerCallingForwardTicker = new Ticker(
                callForwardDefaultNoAnswer_DEF);
        allNoAnswerCallingForwardForm.setTicker(allNoAnswerCallingForwardTicker);

        allNoAnswerCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK, 1);
        allNoAnswerCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        allNoAnswerCallingForwardForm.addCommand(
                allNoAnswerCallForwardDialCommand);
        allNoAnswerCallingForwardForm.addCommand(
                allNoAnswerCallForwardBackCommand);
        allNoAnswerCallingForwardForm.setCommandListener(this);

        // Upptaget/Inget svar


        allBusyNoAnswerCallingForwardForm = new Form(
                enterDefaultEnterNumber_DEF);
        allBusyNoAnswerCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        allBusyNoAnswerCallingForwardTicker = new Ticker(
                callForwardDefaultBusyNoAnswer_DEF);
        allBusyNoAnswerCallingForwardForm.setTicker(
                allBusyNoAnswerCallingForwardTicker);

        allBusyNoAnswerCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK,
                1);
        allBusyNoAnswerCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        allBusyNoAnswerCallingForwardForm.addCommand(
                allBusyNoAnswerCallForwardDialCommand);
        allBusyNoAnswerCallingForwardForm.addCommand(
                allBusyNoAnswerCallForwardBackCommand);
        allBusyNoAnswerCallingForwardForm.setCommandListener(this);

        //------------------- Röstbrevlåda -------------------------------------

        voiceMessageForm = new Form("Voicemail");

        voiceMessageTextField = new TextField("Enter number: ", "", 32,
                                              TextField.PHONENUMBER);

        voiceMessageSaveCommand = new Command("Save", Command.OK, 1);
        voiceMessageCancelCommand = new Command("Cancel", Command.BACK, 2);
        voiceMessageBackCommand = new Command("Back", Command.BACK, 3);

        voiceMessageForm.addCommand(voiceMessageBackCommand);
        voiceMessageForm.addCommand(voiceMessageCancelCommand);
        voiceMessageForm.addCommand(voiceMessageSaveCommand);
        voiceMessageForm.setCommandListener(this);

        //------------- PRE-EDIT-FORM ------------------------------------------

        preEditForm = new Form(settingsDefaultPreEditCode_DEF);
        preEditTextField = new TextField(enterDefaultEngerCharacter_DEF, "", 2,
                                         TextField.PHONENUMBER);

        preEditSaveCommand = new Command(genDefaultSave_DEF, Command.OK, 1);
        preEditBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        preEditCancelCommand = new Command(genDefaultCancel_DEF, Command.BACK,
                                           3);

        preEditForm.addCommand(preEditSaveCommand);
        preEditForm.addCommand(preEditBackCommand);
        preEditForm.addCommand(preEditCancelCommand);
        preEditForm.setCommandListener(this);

        //------------- VOICE-EDIT-FORM ----------------------------------------

        voiceEditForm = new Form(voiceMailDefaultEditVoicemail_DEF);
        voiceEditTextField = new TextField(enterDefaultEnterNumber_DEF, "", 5,
                                           TextField.NUMERIC);
        voiceExtensionEditTextField = new TextField(
                enterDefaultEnterExtension_DEF, "", 5, TextField.NUMERIC);

        voiceEditSaveCommand = new Command(genDefaultSave_DEF, Command.OK, 1);
        voiceEditBackcommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        voiceEditCancelCommand = new Command(genDefaultCancel_DEF, Command.BACK,
                                             3);

        voiceEditForm.addCommand(voiceEditSaveCommand);
        voiceEditForm.addCommand(voiceEditBackcommand);
        voiceEditForm.addCommand(voiceEditCancelCommand);
        voiceEditForm.setCommandListener(this);

        //------------- ExternForwardTextBox -----------------------------------

        // Alla samtal textbox


        externCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        externCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        externCallingForwardTicker = new Ticker(callForwardDefaultAllCalls_DEF);
        externCallingForwardForm.setTicker(externCallingForwardTicker);

        externCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK, 1);
        externCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        externCallingForwardForm.addCommand(externCallForwardDialCommand);
        externCallingForwardForm.addCommand(externCallForwardBackCommand);
        externCallingForwardForm.setCommandListener(this);

        // Upptagen


        externBusyCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        externBusyCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        externBusyCallingForwardTicker = new Ticker(callForwardDefaultBusy_DEF);
        externBusyCallingForwardForm.setTicker(externBusyCallingForwardTicker);

        externBusyCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK, 1);
        externBusyCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        externBusyCallingForwardForm.addCommand(
                externBusyCallForwardDialCommand);
        externBusyCallingForwardForm.addCommand(
                externBusyCallForwardBackCommand);
        externBusyCallingForwardForm.setCommandListener(this);

        // Inget svar


        externNoAnswerCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        externNoAnswerCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        externNoAnswerCallingForwardTicker = new Ticker(
                callForwardDefaultNoAnswer_DEF);
        externNoAnswerCallingForwardForm.setTicker(
                externNoAnswerCallingForwardTicker);

        externNoAnswerCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK,
                1);
        externNoAnswerCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        externNoAnswerCallingForwardForm.addCommand(
                externNoAnswerCallForwardDialCommand);
        externNoAnswerCallingForwardForm.addCommand(
                externNoAnswerCallForwardBackCommand);
        externNoAnswerCallingForwardForm.setCommandListener(this);

        // Upptaget/Inget svar


        externBusyNoAnswerCallingForwardForm = new Form(
                enterDefaultEnterNumber_DEF);
        externBusyNoAnswerCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        externBusyNoAnswerCallingForwardTicker = new Ticker(
                callForwardDefaultBusyNoAnswer_DEF);
        externBusyNoAnswerCallingForwardForm.setTicker(
                externBusyNoAnswerCallingForwardTicker);

        externBusyNoAnswerCallForwardDialCommand = new Command(
                genDefaultSend_DEF,
                Command.OK, 1);
        externBusyNoAnswerCallForwardBackCommand = new Command(
                genDefaultBack_DEF,
                Command.BACK, 2);

        externBusyNoAnswerCallingForwardForm.addCommand(
                externBusyNoAnswerCallForwardDialCommand);
        externBusyNoAnswerCallingForwardForm.addCommand(
                externBusyNoAnswerCallForwardBackCommand);
        externBusyNoAnswerCallingForwardForm.setCommandListener(this);

        //------------- InternForwardTextBox -----------------------------------

        // Alla samtal textbox

        internCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        internCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        internCallingForwardTicker = new Ticker(callForwardDefaultAllCalls_DEF);
        internCallingForwardForm.setTicker(internCallingForwardTicker);

        internCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK, 1);
        internCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        internCallingForwardForm.addCommand(internCallForwardDialCommand);
        internCallingForwardForm.addCommand(internCallForwardBackCommand);
        internCallingForwardForm.setCommandListener(this);

        // Upptagen


        internBusyCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        internBusyCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        internBusyCallingForwardTicker = new Ticker(callForwardDefaultBusy_DEF);
        internBusyCallingForwardForm.setTicker(internBusyCallingForwardTicker);

        internBusyCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK, 1);
        internBusyCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        internBusyCallingForwardForm.addCommand(
                internBusyCallForwardDialCommand);
        internBusyCallingForwardForm.addCommand(
                internBusyCallForwardBackCommand);
        internBusyCallingForwardForm.setCommandListener(this);

        // Inget svar


        internNoAnswerCallingForwardForm = new Form(enterDefaultEnterNumber_DEF);
        internNoAnswerCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        internNoAnswerCallingForwardTicker = new Ticker(
                callForwardDefaultNoAnswer_DEF);
        internNoAnswerCallingForwardForm.setTicker(
                internNoAnswerCallingForwardTicker);

        internNoAnswerCallForwardDialCommand = new Command(genDefaultSend_DEF,
                Command.OK,
                1);
        internNoAnswerCallForwardBackCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        internNoAnswerCallingForwardForm.addCommand(
                internNoAnswerCallForwardDialCommand);
        internNoAnswerCallingForwardForm.addCommand(
                internNoAnswerCallForwardBackCommand);
        internNoAnswerCallingForwardForm.setCommandListener(this);

        // Upptaget/Inget svar

        internBusyNoAnswerCallingForwardForm = new Form(
                enterDefaultEnterNumber_DEF);
        internBusyNoAnswerCallingForwardTextField = new TextField("", "", 25,
                TextField.PHONENUMBER);
        internBusyNoAnswerCallingForwardTicker = new Ticker(
                callForwardDefaultBusyNoAnswer_DEF);
        internBusyNoAnswerCallingForwardForm.setTicker(
                internBusyNoAnswerCallingForwardTicker);

        internBusyNoAnswerCallForwardDialCommand = new Command(
                genDefaultSend_DEF,
                Command.OK, 1);
        internBusyNoAnswerCallForwardBackCommand = new Command(
                genDefaultBack_DEF,
                Command.BACK, 2);

        internBusyNoAnswerCallingForwardForm.addCommand(
                internBusyNoAnswerCallForwardDialCommand);
        internBusyNoAnswerCallingForwardForm.addCommand(
                internBusyNoAnswerCallForwardBackCommand);
        internBusyNoAnswerCallingForwardForm.setCommandListener(this);

        //------------- logoffGroupForm --------------------------------------


        logoffGroupForm = new Form(enterDefaultEnterGroupNumber_DEF);
        logoffGroupTextField = new TextField("", "", 5, TextField.NUMERIC);
        logoffGroupTicker = new Ticker(groupsDefaultLogoutSpecificGroup_DEF);
        logoffGroupForm.setTicker(logoffGroupTicker);

        logoffGroupDialCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        logoffGroupBackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                             2);

        logoffGroupForm.addCommand(logoffGroupDialCommand);
        logoffGroupForm.addCommand(logoffGroupBackCommand);
        logoffGroupForm.setCommandListener(this);

        //------------- loginGroupForm --------------------------------------


        loginGroupForm = new Form(enterDefaultEnterGroupNumber_DEF);
        loginGroupTextField = new TextField("", "", 5, TextField.NUMERIC);
        loginGroupTicker = new Ticker(groupsDefaultLoginSpecificGroup_DEF);
        loginGroupForm.setTicker(loginGroupTicker);

        loginGroupDialCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        loginGroupBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);

        loginGroupForm.addCommand(loginGroupDialCommand);
        loginGroupForm.addCommand(loginGroupBackCommand);
        loginGroupForm.setCommandListener(this);

        //------------- Out Until ------------------------------------------------


        outUntilForm = new Form(enterDefaultEnterMMDD_DEF);
        outUntilTextField = new TextField("", "", 4, TextField.NUMERIC);
        outUntilTicker = new Ticker(absentDefaultOutUntil_DEF);
        outUntilForm.setTicker(outUntilTicker);

        outUntilDialCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        outUntilBackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                          2);

        outUntilForm.addCommand(outUntilDialCommand);
        outUntilForm.addCommand(outUntilBackCommand);
        outUntilForm.setCommandListener(this);

        //------------- Back At ------------------------------------------------


        backAtForm = new Form(enterDefaultEnterHHMM_DEF);
        backAtTextField = new TextField("", "", 4, TextField.NUMERIC);
        backAtTicker = new Ticker(absentDefaultBackAt_DEF);
        backAtForm.setTicker(backAtTicker);

        backAtDialCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        backAtCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);

        backAtForm.addCommand(backAtDialCommand);
        backAtForm.addCommand(backAtCommand);
        backAtForm.setCommandListener(this);

        //------------- At Extension  ------------------------------------------

        atExtForm = new Form(enterDefaultEnterExtension_DEF);
        atExtTextField = new TextField("", "", 5, TextField.PHONENUMBER);
        atExtTicker = new Ticker(absentDefaultAtExt_DEF);
        atExtForm.setTicker(atExtTicker);

        absentDialCommand = new Command(genDefaultSend_DEF, Command.OK, 1);
        atExtBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);

        atExtForm.addCommand(absentDialCommand);
        atExtForm.addCommand(atExtBackCommand);
        atExtForm.setCommandListener(this);

        //------------- AbsentList ---------------------------------------------

        absentList = new List("", Choice.IMPLICIT); // skapar en lista
        absentList.setTitle(null);
        absentTicker = new Ticker(absentDefaultSetPresence_DEF);
        absentList.setTicker(absentTicker);

        editAbsentSystemCommand = new Command(genDefaultEdit_DEF, Command.OK, 1);
        ExitCommandAbsentList = new Command(genDefaultBack_DEF,
                                            Command.BACK, 2);
        String system = "";
        try {

            Image image1c = Image.createImage(
                    "/prg_icon/taborthanvisning24.png");
            Image image2c = Image.createImage("/prg_icon/ute24.png");
            Image image3c = Image.createImage("/prg_icon/upptagen24.png");
            Image image4c = Image.createImage("/prg_icon/systemphone24.png");
            Image image5c = Image.createImage("/prg_icon/tillbakakl24.png");
            Image image6c = Image.createImage("/prg_icon/tillbakaden24.png");
            Image image7c = Image.createImage("/prg_icon/konference24.png");

            absentList.append(absentDefaultRemovePresence_DEF, image1c);
            absentList.append(absentDeafaultWillReturnSoon_DEF, image2c);
            absentList.append(absentDefaultGoneHome_DEF, image3c);
            absentList.append(absentDefaultAtExt_DEF, image4c);
            absentList.append(absentDefaultBackAt_DEF, image5c);
            absentList.append(absentDefaultOutUntil_DEF, image6c);
            absentList.append(absentDefaultInAMeeting_DEF, image7c);
            absentList.append(this.systemText_1_Attribute, null); // dynamiskt kan ändra namn i databasen.
            absentList.append(this.systemText_2_Attribute, null);
            absentList.append(this.personalText_Attribute, null);

            absentList.addCommand(ExitCommandAbsentList);
            absentList.addCommand(editAbsentSystemCommand);
            absentList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //------------- VoiceMailList ----------------------------------------

        voiceMailList = new List("", Choice.IMPLICIT); // skapar en lista
        voiceMailList.setTitle(null);
        voiceMailTicker = new Ticker(voiceMailDefaultVoiceMail_DEF);
        voiceMailList.setTicker(voiceMailTicker);

        voiceMailBackCommand = new Command(genDefaultBack_DEF,
                                           Command.BACK, 2);
        try {
            Image imageCallForward = Image.createImage(
                    "/prg_icon/rostbrevlada24.png");

            voiceMailList.append("", imageCallForward);
            voiceMailList.append("", imageCallForward);
            voiceMailList.append(callForwardDefaultRemove_DEF, imageCallForward);

            voiceMailList.addCommand(voiceMailBackCommand);
            voiceMailList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //------------- CallforwardList ----------------------------------------

        callForwardList = new List("", Choice.IMPLICIT); // skapar en lista
        callForwardList.setTitle(null);
        callForwardTicker = new Ticker(callForwardDefault_DEF);
        callForwardList.setTicker(callForwardTicker);

        backCallForwardCommand = new Command(genDefaultBack_DEF,
                                             Command.BACK,
                                             2);
        try {
            Image imageCallForward = Image.createImage(
                    "/prg_icon/vidarekoppling24.png");

            callForwardList.append(callForwardDefaultAllCalls_DEF,
                                   imageCallForward);
            callForwardList.append(callForwardDefaultExternCalls_DEF,
                                   imageCallForward);
            callForwardList.append(callForwardDefaultInternCalls_DEF,
                                   imageCallForward);

            callForwardList.addCommand(backCallForwardCommand);
            callForwardList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //------------- AllCallforwardList ----------------------------------------

        allCallForwardList = new List("", Choice.IMPLICIT); // skapar en lista
        allCallForwardList.setTitle(null);
        allCallForwardTicker = new Ticker(callForwardDefaultAllCalls_DEF);
        allCallForwardList.setTicker(allCallForwardTicker);

        backAllCallForwardCommand = new Command(genDefaultBack_DEF,
                                                Command.BACK, 2);

        try {
            Image imageAllCallForward = Image.createImage(
                    "/prg_icon/vidarekoppling24.png");

            allCallForwardList.append(callForwardDefaultRemove_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(callForwardDefaultDontDisturb_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(callForwardDefaultAllCalls_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(callForwardDefaultBusy_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(callForwardDefaultNoAnswer_DEF,
                                      imageAllCallForward);
            allCallForwardList.append(callForwardDefaultBusyNoAnswer_DEF,
                                      imageAllCallForward);

            allCallForwardList.addCommand(backAllCallForwardCommand);
            allCallForwardList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //------------- ExternCallforwardList ----------------------------------

        externCallForwardList = new List("", Choice.IMPLICIT); // skapar en lista
        externCallForwardList.setTitle(null);
        externCallForwardTicker = new Ticker(callForwardDefaultExternCalls_DEF);
        externCallForwardList.setTicker(externCallForwardTicker);

        backExternCallForwardCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        try {
            Image imageExternCallForward = Image.createImage(
                    "/prg_icon/vidarekoppling24.png");

            externCallForwardList.append(callForwardDefaultRemove_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(callForwardDefaultDontDisturb_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(callForwardDefaultAllCalls_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(callForwardDefaultBusy_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(callForwardDefaultNoAnswer_DEF,
                                         imageExternCallForward);
            externCallForwardList.append(callForwardDefaultBusyNoAnswer_DEF,
                                         imageExternCallForward);

            externCallForwardList.addCommand(backExternCallForwardCommand);
            externCallForwardList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //------------- InternCallforwardList ----------------------------------

        internCallForwardList = new List("", Choice.IMPLICIT); // skapar en lista
        internCallForwardList.setTitle(null);
        internCallForwardTicker = new Ticker(callForwardDefaultInternCalls_DEF);
        internCallForwardList.setTicker(internCallForwardTicker);

        backInternCallForwardCommand = new Command(genDefaultBack_DEF,
                Command.BACK, 2);

        try {
            Image imageInternCallForward = Image.createImage(
                    "/prg_icon/vidarekoppling24.png");

            internCallForwardList.append(callForwardDefaultRemove_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(callForwardDefaultDontDisturb_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(callForwardDefaultAllCalls_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(callForwardDefaultBusy_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(callForwardDefaultNoAnswer_DEF,
                                         imageInternCallForward);
            internCallForwardList.append(callForwardDefaultBusyNoAnswer_DEF,
                                         imageInternCallForward);

            internCallForwardList.addCommand(backInternCallForwardCommand);
            internCallForwardList.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //internCallForwardList

        //--------- SettingsList för Språkval ----------------------------------

        language_List = new List(settingsDefaultLanguage_DEF, Choice.IMPLICIT);
        try {

            Image imageAus = Image.createImage("/prg_icon/Austria24.png");
            Image imageBel = Image.createImage("/prg_icon/Belgium24.png");
            Image imageDen = Image.createImage("/prg_icon/Denmark24.png");
            Image imageFin = Image.createImage("/prg_icon/Finland24.png");
            Image imageFra = Image.createImage("/prg_icon/France24.png");
            Image imageGer = Image.createImage("/prg_icon/Germany24.png");
            Image imageGre = Image.createImage("/prg_icon/Greece24.png");
            Image imageIta = Image.createImage("/prg_icon/Italy24.png");
            Image imageLux = Image.createImage("/prg_icon/Luxembourg24.png");
            Image imageNet = Image.createImage("/prg_icon/Netherlands24.png");
            Image imageNor = Image.createImage("/prg_icon/Norway24.png");
            Image imagePor = Image.createImage("/prg_icon/Portugal24.png");
            Image imageSpa = Image.createImage("/prg_icon/Spain24.png");
            Image imageSwe = Image.createImage("/prg_icon/Sweden24.png");
            Image imageSwi = Image.createImage("/prg_icon/Switzerland24.png");
            Image imageTur = Image.createImage("/prg_icon/Turkey24.png");
            Image imageEng = Image.createImage("/prg_icon/United_Kingdom24.png");

            language_List.append("Danish", imageDen);
            language_List.append("Dutch", imageNet);
            language_List.append("English", imageEng);
            language_List.append("Finnish", imageFin);
            language_List.append("French", imageFra);
            language_List.append("German", imageGer);
            language_List.append("Norwegian", imageNor);
            language_List.append("Italian", imageIta);
            language_List.append("Spanish", imageSpa);
            language_List.append("Swedish", imageSwe);

            /* language_List.append("Austria", imageAus);
             language_List.append("Belgium", imageBel);
             language_List.append("Greece", imageGre);
             language_List.append("Luxembourg", imageLux);
             language_List.append("Portugal", imagePor);
             language_List.append("Switzerland", imageSwi);
             language_List.append("Turkey", imageTur);*/


            languageListCancelCommand = new Command(genDefaultCancel_DEF,
                    Command.CANCEL, 2);

            language_List.addCommand(languageListCancelCommand);
            language_List.setCommandListener(this);

        } catch (IOException ex) {
            System.out.println("Unable to Find or Read .png file");
        }

        //--------- SettingsList.

        pbx_List = new List(settingsDefaultEditPBXAccess_DEF, Choice.IMPLICIT);
        pbx_List.append(accessPBXDefault_DEF, null);
        pbx_List.append(settingsDefaultPreEditCode_DEF, null);
        pbx_List.append(voiceMailDefaultEditVoicemail_DEF, null);
        pbx_List.append(settingsDefaultLanguage_DEF, null);
        pbx_List.append("Operator Voicemail", null);

        settingsListCancelCommand = new Command(genDefaultCancel_DEF,
                                                Command.CANCEL, 2);

        pbx_List.addCommand(settingsListCancelCommand);
        pbx_List.setCommandListener(this);

        // accessPBXDefault_DEF autoAccessDefault_DEF accessViaPINCodeDefault_DEF


        //-------- Select PBX TYPE Pbx_List_Type_backCommand

        pbx_List_type = new List(settingsDefaultEditPBXAccess_DEF,
                                 Choice.IMPLICIT);
        pbx_List_type.append(autoAccessDefault_DEF, null);
        pbx_List_type.append(accessViaPINCodeDefault_DEF, null);

        settingsPBXListCancelCommand = new Command(genDefaultCancel_DEF,
                Command.CANCEL, 1);
        Pbx_List_Type_backCommand = new Command(genDefaultBack_DEF,
                                                Command.BACK, 2);

        pbx_List_type.addCommand(settingsPBXListCancelCommand);
        pbx_List_type.addCommand(Pbx_List_Type_backCommand);
        pbx_List_type.setCommandListener(this);

        // ------------ Edit-Absent-forms --------------------------------------

        // System Attributes_1
        editSystemForm_1 = new Form(absentDefaultSystemAttOne_DEF);

        editTextSystemTextField_1 = new TextField(callExtensionDefaultName_DEF,
                                                  "", 40,
                                                  TextField.ANY);

        saveEditSystemCommand_1 = new Command(genDefaultSave_DEF, Command.OK, 1);
        cancelEditSystemCommand_1 = new Command(genDefaultCancel_DEF,
                                                Command.BACK, 2);

        editSystemForm_1.addCommand(saveEditSystemCommand_1);
        editSystemForm_1.addCommand(cancelEditSystemCommand_1);
        editSystemForm_1.setCommandListener(this);

        // System Attributes_2
        editSystemForm_2 = new Form(absentDeafaultSystemAttTwo_DEF);

        editTextSystemTextField_2 = new TextField(callExtensionDefaultName_DEF,
                                                  "", 40,
                                                  TextField.ANY);

        saveEditSystemCommand_2 = new Command(genDefaultSave_DEF, Command.OK, 1);
        cancelEditSystemCommand_2 = new Command(genDefaultCancel_DEF,
                                                Command.BACK, 2);

        editSystemForm_2.addCommand(saveEditSystemCommand_2);
        editSystemForm_2.addCommand(cancelEditSystemCommand_2);
        editSystemForm_2.setCommandListener(this);

        // Personal Attributes_1
        editPersonalForm = new Form(absentDefaultPersonalAtt_DEF);

        editTextPersonalTextField = new TextField(callExtensionDefaultName_DEF,
                                                  "", 40,
                                                  TextField.ANY);

        saveEditPersonalCommand = new Command(genDefaultSave_DEF, Command.OK, 1);
        cancelEditPersonalCommand = new Command(genDefaultCancel_DEF,
                                                Command.BACK, 2);

        editPersonalForm.addCommand(saveEditPersonalCommand);
        editPersonalForm.addCommand(cancelEditPersonalCommand);
        editPersonalForm.setCommandListener(this);

        //---------------- EDITSETTINGFORM -----------------------------------

        editSettingForm = new Form(accessViaPINCodeDefault_DEF);

        accessNumbers = new TextField(settingsDefaultLineAccess_DEF, "", 32,
                                      TextField.NUMERIC);

        editSwitchBoardNumber = new TextField(
                settingsDefaultSwitchboardNumber_DEF, "",
                32,
                TextField.PHONENUMBER);

        editExtensionNumber = new TextField(settingsDefaultExtension_DEF, "",
                                            32,
                                            TextField.PHONENUMBER);

        editPinCodeNumber = new TextField(settingsDefaultPINcode_DEF, "", 32,
                                          TextField.NUMERIC);

        editSettingBackCommand = new Command(genDefaultBack_DEF,
                                             Command.BACK, 2);
        editSettingCancelCommand = new Command(genDefaultCancel_DEF,
                                               Command.BACK,
                                               3);
        editSettingSaveCommand = new Command(genDefaultSave_DEF, Command.OK, 1);

        editSettingForm.addCommand(editSettingBackCommand);
        editSettingForm.addCommand(editSettingCancelCommand);
        editSettingForm.addCommand(editSettingSaveCommand);
        editSettingForm.setCommandListener(this);

        //---------------- EDITSETTINGFORM 2 -----------------------------------

        editSettingForm2 = new Form(autoAccessDefault_DEF);

        accessNumbers2 = new TextField(settingsDefaultLineAccess_DEF, "", 32,
                                       TextField.NUMERIC);

        editSwitchBoardNumber2 = new TextField(
                settingsDefaultSwitchboardNumber_DEF, "",
                32,
                TextField.PHONENUMBER);

        editSettingBackCommand2 = new Command(genDefaultBack_DEF,
                                              Command.BACK, 2);
        editSettingCancelCommand2 = new Command(genDefaultCancel_DEF,
                                                Command.BACK,
                                                3);
        editSettingSaveCommand2 = new Command(genDefaultSave_DEF, Command.OK, 1);

        editSettingForm2.addCommand(editSettingBackCommand2);
        editSettingForm2.addCommand(editSettingCancelCommand2);
        editSettingForm2.addCommand(editSettingSaveCommand2);
        editSettingForm2.setCommandListener(this);

        // ---------------- Country number -------------------------------------

        countryForm = new Form(settingsDefaultCountryCode_DEF);

        countryField = new TextField(settingsDefaultSelectCountryCode_DEF, "",
                                     4,
                                     TextField.NUMERIC);

        countryBackCommand = new Command(genDefaultBack_DEF, Command.BACK,
                                         2);
        countryCancelCommand = new Command(genDefaultCancel_DEF, Command.BACK,
                                           3);
        countrySaveCommand = new Command(genDefaultSave_DEF, Command.OK, 1);

        countryForm.addCommand(countryBackCommand);
        countryForm.addCommand(countryCancelCommand);
        countryForm.addCommand(countrySaveCommand);
        countryForm.setCommandListener(this);

        //--------------- Alert-Screen -----------------------------------------

        //Exit alert....
        try {
            Image alertExitImage = Image.createImage("/prg_icon/exit2_64.png");
            alertExit = new Alert(genDefaultExit_DEF,
                                  exitDefaultExitTheProgramYesOrNo_DEF,
                                  alertExitImage, AlertType.CONFIRMATION);

            alertExit.setTimeout(Alert.FOREVER);

            confirmExitYESCommand = new Command(genDefaultYes_DEF,
                                                Command.EXIT, 1);
            confirmExitNOCommand = new Command(exitDefaultNo_DEF,
                                               Command.OK, 2);

            alertExit.addCommand(confirmExitYESCommand);
            alertExit.addCommand(confirmExitNOCommand);
            alertExit.setCommandListener(this);
        } catch (IOException ex5) {
        }

        //--------------- Alert edit Settings ----------------------------------

        try {
            Image alertEditSettingImage = Image.createImage(
                    "/prg_icon/save.png");
            alertEditSettings = new Alert(alertDefaultSaveChanges_DEF,
                                          alsertDefaultChangesSave_DEF,
                                          alertEditSettingImage,
                                          AlertType.CONFIRMATION);
            setDataStore();
            upDateDataStore();
            alertEditSettings.setTimeout(2000);
        } catch (InvalidRecordIDException ex7) {
        } catch (RecordStoreNotOpenException ex7) {
        } catch (RecordStoreException ex7) {
        } catch (IOException ex7) {
        }

        //--------------- Alert-Screen -----------------------------------------

        // Alert on

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertON = new Alert("Mex Server Info!", "", alertInfo,
                                AlertType.INFO);
            alertON.setTimeout(1000);

        } catch (IOException ex11) {
        }

        //--------------- Alert off --------------------------------------------

        try {

            Image alertMexOff = Image.createImage("/prg_icon/mexoff.png");
            alertOFF = new Alert("Mex Server Info!", "", alertMexOff,
                                 AlertType.INFO);
            alertOFF.setTimeout(1000);

        } catch (IOException ex11) {
        }

        //--------------- Alert Debug NOK -------------------------------------

        try {

            Image alertInfo = Image.createImage("/prg_icon/info.png");
            alertDebugNOK = new Alert("Mex Server Info!", "", alertInfo,
                                      AlertType.INFO);
            alertDebugNOK.setTimeout(Alert.FOREVER);

        } catch (IOException ex11) {
        }

        //--------------- Alert Restart program --------------------------------

        try {
            Image alertRestartImage = Image.createImage("/prg_icon/restart.png");
            alertRestarting = new Alert(alertDefaultSaveChanges_DEF,
                                        exitDefaultRestartProgram_DEF,
                                        alertRestartImage,
                                        AlertType.CONFIRMATION);
            setDataStore();
            upDateDataStore();
            alertRestarting.setTimeout(Alert.FOREVER);
        } catch (InvalidRecordIDException ex9) {
        } catch (RecordStoreNotOpenException ex9) {
        } catch (RecordStoreException ex9) {
        } catch (IOException ex9) {
        }

        //-------------- Alert Error -------------------------------------------

        alerterror = new Alert(alertDefaultCountryCodeError_DEF,
                               alertDefaultErrorChangeTo_DEF + "+" +
                               countryCode_PBX
                               + alertDefaultInstedOf_DEF + "00" +
                               countryCode_PBX, null,
                               AlertType.CONFIRMATION);
        alerterror.setTimeout(Alert.FOREVER);

        //------------ Alert info ----------------------------------------------

        try {
            Image alertInfoImage = Image.createImage("/prg_icon/info.png");

            alert = new Alert("", "", alertInfoImage, AlertType.INFO);
            alert.setTimeout(2000);
        } catch (IOException ex) {
        }

        // ------ fristående kommandon -----------------------------------------

        AboutCommand = new Command("Version 2.0" /*settingsDefaultAbout_DEF*/,
                                   Command.HELP, 3);
        goBackCommand = new Command(genDefaultBack_DEF, Command.BACK, 2);
        helpCommand = new Command(settingsDefaultHelp_DEF, Command.HELP, 3);
        systemCommand = new Command("System", Command.OK, 1);

        //------------ kontrollerar om CONF.java är satt -----------------------

        if (checkStatus_PBX != "1") {

            try {
                setPBXConfig();
            } catch (InvalidRecordIDException ex2) {
            } catch (RecordStoreNotOpenException ex2) {
            } catch (RecordStoreException ex2) {
            }

        }

    }

    public void setPBXConfig() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        this.mexONOFF_PBX = config.MEX; // Plats 16.
        this.switchBoardNumber_PBX = config.SWTBNR; // KLAR
        this.accessNumber_PBX = config.LA; // KLAR
        this.countryCode_PBX = config.CCODE; // KLAR
        this.dbg_PBX = config.DBG; // KLAR
        this.HGP_PBX = config.HGP; // KLAR
        this.voiceMailOperator_PBX = config.VM; // operatör KLAR 12
        this.pinCodeNumber_PBX = config.PCODE; // KLAR
        this.extensionNumber_PBX = config.ANKN;
        this.lang_PBX = config.LANG;
        this.precode_PBX = config.PRECODE;
        this.voiceMailSwitchboard_PBX = config.PBXVM;
        this.demo_PBX = config.DEMO;
        this.checkStatus_PBX = "1";
        this.companyName_PBX = config.COMPANYNAME;
        this.namne_PBX = config.NAME;



        if (mexONOFF_PBX.equals(null) || mexONOFF_PBX.equals("")) {

            mexONOFF_PBX = "0";
        }
        if (switchBoardNumber_PBX.equals(null) ||
            switchBoardNumber_PBX.equals("")) {

            switchBoardNumber_PBX = "+46";
        }
        if (accessNumber_PBX.equals(null) || accessNumber_PBX.equals("")) {

            accessNumber_PBX = "0";
        }
        if (countryCode_PBX.equals(null) || countryCode_PBX.equals("")) {

            countryCode_PBX = "46";
        }
        if (dbg_PBX.equals(null) || dbg_PBX.equals("")) {

            dbg_PBX = "0";
        }
        if (HGP_PBX.equals(null) || HGP_PBX.equals("")) {

            HGP_PBX = "197";
        }
        if (voiceMailOperator_PBX.equals(null) ||
            voiceMailOperator_PBX.equals("")) {

            voiceMailOperator_PBX = "0";
        }
        if (extensionNumber_PBX.equals(null) || extensionNumber_PBX.equals("")) {

            extensionNumber_PBX = "0";
        }
        if (lang_PBX.equals(null) || lang_PBX.equals("")) {

            lang_PBX = "0";
        }
        if (demo_PBX.equals(null) || demo_PBX.equals("")) {

            demo_PBX = "0";
        }
        if (companyName_PBX.equals(null) || companyName_PBX.equals("")) {

            companyName_PBX = "Company";
        }
        if (namne_PBX.equals(null) || namne_PBX.equals("")) {

            namne_PBX = "Name";
        }
        if (pinCodeNumber_PBX.equals(null) || pinCodeNumber_PBX.equals("")) {

            pinCodeNumber_PBX = "0";
        }
        if (precode_PBX.equals(null) || precode_PBX.equals("")) {

            precode_PBX = "*7";
        }
        if (voiceMailSwitchboard_PBX.equals(null) ||
            voiceMailSwitchboard_PBX.equals("")) {

            voiceMailSwitchboard_PBX = "0";
        }

        try {
            recStore.setRecord(16, mexONOFF_PBX.getBytes(), 0,
                               mexONOFF_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(5, switchBoardNumber_PBX.getBytes(), 0,
                               switchBoardNumber_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(4, accessNumber_PBX.getBytes(), 0,
                               accessNumber_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(15, countryCode_PBX.getBytes(), 0,
                               countryCode_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(13, dbg_PBX.getBytes(), 0, dbg_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(17, HGP_PBX.getBytes(), 0, HGP_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(12, voiceMailOperator_PBX.getBytes(), 0,
                               voiceMailOperator_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(10, pinCodeNumber_PBX.getBytes(), 0,
                               pinCodeNumber_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(9, extensionNumber_PBX.getBytes(), 0,
                               extensionNumber_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(19, lang_PBX.getBytes(), 0, lang_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(20, demo_PBX.getBytes(), 0, demo_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(21, checkStatus_PBX.getBytes(), 0,
                               checkStatus_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(27, precode_PBX.getBytes(), 0,
                               precode_PBX.length());
        } catch (Exception e) {

        }
        try {
            recStore.setRecord(18, voiceMailSwitchboard_PBX.getBytes(), 0,
                               voiceMailSwitchboard_PBX.length());
        } catch (Exception e) {

        }

        String demo = getDemoStatus();

        System.out.println("SKRIV UT DEMO >> " + demo);

        if (demo.equals("1")) {

            try {
                controllString();
            } catch (InvalidRecordIDException ex) {
            } catch (RecordStoreNotOpenException ex) {
            } catch (RecordStoreException ex) {
            }
            try {
                controllDate();
            } catch (InvalidRecordIDException ex1) {
            } catch (RecordStoreNotOpenException ex1) {
            } catch (RecordStoreException ex1) {
            } catch (IOException ex1) {
            }

            this.ViewDateString = setViewDateString();

        }

        if (demo.equals("0")) {

            this.ViewDateString = "Enterprise License";

        }
        closeRecStore();

    }


    public Form getEditVoiceMessageForm() {

        voiceMessageForm.deleteAll();
        openRecStore();
        voiceMessageTextField.setString(voiceMailOperator_PBX);
        voiceMessageForm.append(voiceMessageTextField);
        closeRecStore();

        return voiceMessageForm;
    }


    public void sendRequest(String message) {
        this.request = message;
        new Thread() {
            public void run() {
                sendMessage();
            }
        }.start();
    }

    public void sendMessage() {
        try {
            StreamConnection conn = (StreamConnection) Connector.open(url);
            OutputStream out = conn.openOutputStream();
            byte[] buf = request.getBytes();
            out.write(buf, 0, buf.length);
            out.flush();
            out.close();

            byte[] data = new byte[256];
            InputStream in = conn.openInputStream();
            int actualLength = in.read(data);
            String response = new String(data, 0, actualLength);
            setAlertMEXONOFF(response);
            in.close();
            conn.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();

        }
    }

    public void setAlertMEXONOFF(String resp) {

        this.ResponceMessage = resp;

        String controll = this.ResponceMessage.substring(0, 2);
        String procentString = this.ResponceMessage.substring(0, 1);

        if (controll.equals("sN")) { // Redan på

            String setStringResponse = this.ResponceMessage.substring(5);

            setStringResponse.length();

            alertDebugNOK.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertDebugNOK);

        }

        if (controll.equals("eN")) { // Redan avstängd

            String setStringResponse = this.ResponceMessage.substring(5);

            setStringResponse.length();

            alertDebugNOK.setString(setStringResponse);

            Display.getDisplay(this).setCurrent(alertDebugNOK);

        }

        if (controll.equals("sO")) { // Mex on

            alertON.setString("Mex Started!");

            Display.getDisplay(this).setCurrent(alertON);

        }

        if (controll.equals("eO")) { // Mex off

            alertOFF.setString("Mex Stopped!");

            Display.getDisplay(this).setCurrent(alertOFF);

        }
        if (controll.equals("fO")) { // Mex on

            alertExit.setString(
                    "OBS. Mex är AKTIVERAD! \nÄven om du avslutar!\nVill du avsluta?\nJa eller Nej");

            Display.getDisplay(this).setCurrent(alertExit);

        }

        if (controll.equals("fF")) { // Mex off

            alertExit.setString(
                    "Vill du avsluta?\nJa eller Nej");

            Display.getDisplay(this).setCurrent(alertExit);

        }

    }

    public TextBox getResponseTextBox() {

        response_textbox.setString("");

        return response_textbox;

    }

    public Alert getAlertExit() {

        return alertExit;
    }


    public Form getVoiceEditForm() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        voiceEditForm.deleteAll();
        openRecStore();
        String setVoiceEditCode = getVoiceEditCode();
        voiceEditTextField.setString(setVoiceEditCode);
        voiceEditForm.append(voiceEditTextField);
        voiceExtensionEditTextField.setString(extensionNumber_PBX);
        voiceEditForm.append(voiceExtensionEditTextField);
        closeRecStore();

        return voiceEditForm;
    }

    public Form getPreEditForm() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        preEditForm.deleteAll();
        openRecStore();
        String setPreEditCode = getPreEditCode();
        preEditTextField.setString(setPreEditCode);
        preEditForm.append(preEditTextField);
        closeRecStore();

        return preEditForm;
    }

    public Form getKeyCodeForm() {

        keyCodeForm.deleteAll();
        openRecStore();
        keyCodeTextField.setString(HGP_PBX);
        keyCodeForm.append(keyCodeTextField);
        closeRecStore();

        return keyCodeForm;
    }

    public Form getInternCallingForwardForm() {

        internCallingForwardForm.deleteAll();
        internCallingForwardForm.append(internCallingForwardTextField);

        return internCallingForwardForm;
    }

    public Form getInternBusyNoAnswerCallingForwardForm() {

        internBusyNoAnswerCallingForwardForm.deleteAll();
        internBusyNoAnswerCallingForwardForm.append(
                internBusyNoAnswerCallingForwardTextField);

        return internBusyNoAnswerCallingForwardForm;
    }

    public Form getInternNoAnswerCallingForwardForm() {

        internNoAnswerCallingForwardForm.deleteAll();
        internNoAnswerCallingForwardForm.append(
                internNoAnswerCallingForwardTextField);

        return internNoAnswerCallingForwardForm;
    }

    public Form getInternBusyCallingForwardForm() {

        internBusyCallingForwardForm.deleteAll();
        internBusyCallingForwardForm.append(internBusyCallingForwardTextField);

        return internBusyCallingForwardForm;
    }

    public Form getExternCallingForwardForm() {

        externCallingForwardForm.deleteAll();
        externCallingForwardForm.append(externCallingForwardTextField);

        return externCallingForwardForm;
    }

    public Form getExternBusyNoAnswerCallingForwardForm() {

        externBusyNoAnswerCallingForwardForm.deleteAll();
        externBusyNoAnswerCallingForwardForm.append(
                externBusyNoAnswerCallingForwardTextField);

        return externBusyNoAnswerCallingForwardForm;
    }

    public Form getExternNoAnswerCallingForwardForm() {

        externNoAnswerCallingForwardForm.deleteAll();
        externNoAnswerCallingForwardForm.append(
                externNoAnswerCallingForwardTextField);

        return externNoAnswerCallingForwardForm;
    }

    public Form getExternBusyCallingForwardForm() {

        externBusyCallingForwardForm.deleteAll();

        externBusyCallingForwardForm.append(externBusyCallingForwardTextField);

        return externBusyCallingForwardForm;
    }

    public Form getAllBusyNoAnswerCallingForwardForm() {

        allBusyNoAnswerCallingForwardForm.deleteAll();
        allBusyNoAnswerCallingForwardForm.append(
                allBusyNoAnswerCallingForwardTextField);

        return allBusyNoAnswerCallingForwardForm;
    }

    public Form getAllNoAnswerCallingForwardForm() {

        allNoAnswerCallingForwardForm.deleteAll();
        allNoAnswerCallingForwardForm.append(allNoAnswerCallingForwardTextField);

        return allNoAnswerCallingForwardForm;
    }

    public Form getAllBusyCallingForwardForm() {

        allBusyCallingForwardForm.deleteAll();
        allBusyCallingForwardForm.append(allBusyCallingForwardTextField);

        return allBusyCallingForwardForm;
    }

    public Form getDebugForm() {

        debugForm.deleteAll();
        debugForm.append(debugTextField);

        return debugForm;
    }

    public Form getAllCallingForwardForm() {

        allCallingForwardForm.deleteAll();
        allCallingForwardForm.append(allCallingForwardTextField);

        return allCallingForwardForm;
    }

    public Form getLogoffGroupForm() {

        logoffGroupForm.deleteAll();
        logoffGroupForm.append(logoffGroupTextField);

        return logoffGroupForm;
    }

    public Form getLoginGroupForm() {

        loginGroupForm.deleteAll();
        loginGroupForm.append(loginGroupTextField);

        return loginGroupForm;
    }

    public Form getAtExtForm() {

        atExtForm.deleteAll();

        atExtForm.append(atExtTextField);

        return atExtForm;
    }

    public Form getBackAtForm() {

        backAtForm.deleteAll();

        backAtForm.append(backAtTextField);

        return backAtForm;
    }

    public Form getOutUntilForm() {

        outUntilForm.deleteAll();
        outUntilForm.append(outUntilTextField);

        return outUntilForm;
    }


    public Form getEditSystemForm_1() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        editSystemForm_1.deleteAll();
        openRecStore();
        String setStringSystemAtt_1 = getSystemText_1_Attribute();
        editTextSystemTextField_1.setString(setStringSystemAtt_1);
        editSystemForm_1.append(editTextSystemTextField_1);

        closeRecStore();
        return editSystemForm_1;

    }

    public Form getEditSystemForm_2() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        editSystemForm_2.deleteAll();
        openRecStore();
        String setStringSystemAtt_2 = getSystemText_2_Attribute();
        editTextSystemTextField_2.setString(setStringSystemAtt_2);
        editSystemForm_2.append(editTextSystemTextField_2);
        closeRecStore();

        return editSystemForm_2;

    }

    public Form getEditPersonalForm() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        editPersonalForm.deleteAll();
        openRecStore();
        String setStringPersonalAtt = getPersonalText_Attribute();
        editTextPersonalTextField.setString(setStringPersonalAtt);
        editPersonalForm.append(editTextPersonalTextField);
        closeRecStore();

        return editPersonalForm;

    }


    public int getGameAction(int i) {
        int checkNumber = i;
        if (checkNumber == 1) {
            return 1;
        }
        return 0;
    }

    public List getLanguageList() {

        return language_List;
    }


    public List getSettingsList() {

        return pbx_List;
    }

    public String toString(String b) {

        String s = b;

        return s;
    }

    public void startApp() {

        try {
            setDataStore();
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (RecordStoreException ex) {
        }
        try {
            upDateDataStore();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            controllString();
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreNotOpenException ex2) {
        } catch (RecordStoreException ex2) {
        }

        //register.CheckIMEI("");

        /*Thread i_keycodesetting = new Thread() { // sätter keyCode 'hangup key'
            public void run() {

                String keycodesetting = "i," + "HGP,";

                socketConn.sendRequest(keycodesetting);

         System.out.println("Skriver ut keycodesetting >> " + keycodesetting);

            }
               };
               i_keycodesetting.start();*/


        Display.getDisplay(this).setCurrent(mainList);

    }

    public void pauseApp() {

    }

    public void destroyApp(boolean unconditional) {

    }

    public void getSizeOfScreen() {

        int width = sizeScreen.setWidht(0);
        int height = sizeScreen.setHight(0);

        System.out.println("Width Of Display Screen: " + width);
        System.out.println("Height Of Display Screen: " + height);

    }

    public String checkCallForwardNumber(String checkCallForwardString) { // Justerar landsiffra som är inmatad! Tar bort '+' och lägger in '00' före landssiffran

        String Number = "+";
        String setNumber = "00";
        String validate = checkCallForwardString;
        String validateCountryNumber = this.countryCode_PBX;
        String setNumberNoll = "0";
        String callForward = "";

        if (setNumber.equals(validate.substring(0, 2)) &&
            validateCountryNumber.equals(validate.substring(2, 4))) {

            String setString = validate;

            String deletePartOfString = setString.substring(4); // ta bort plast 0 - 3 ur strängen....

            String setStringTotal = setNumberNoll + deletePartOfString; // sätt ihop strängen setStringTotal

            callForward = setStringTotal;

            return callForward;

        }

        else if (setNumber.equals(validate.substring(0, 2)) &&
                 validateCountryNumber.equals(validate.substring(2, 5))) {

            String setString = validate;

            String deletePartOfString = setString.substring(5); // ta bort plast 0 - 3 ur strängen....

            String setStringTotal = setNumberNoll + deletePartOfString; // sätt ihop strängen setStringTotal

            callForward = setStringTotal;

            return callForward;

        }

        // Om numret innehåller 1 - 5 siffror så är det ett internnummer.

        if (validate.equals(validate.substring(0, 1)) ||
            validate.equals(validate.substring(0, 2)) ||
            validate.equals(validate.substring(0, 3)) ||
            validate.equals(validate.substring(0, 4)) ||
            validate.equals(validate.substring(0, 5))) {

            this.accessCode = "";

            callForward = validate;

            return callForward;

        } // Om numret startar med '+' OCH 'countryCode_PBX' är sann så gör om till '0'.

        if (Number.equals(validate.substring(0, 1)) && // om + == +
            validateCountryNumber.equals(validate.substring(1, 4))) { // Om 358 == 358

            String setString = validate;

            String deletePartOfString = setString.substring(4); // ta bort plast 0 - 3 ur strängen....

            String setStringTotal = setNumberNoll + deletePartOfString; // sätt ihop strängen setStringTotal

            callForward = setStringTotal;

            return callForward;

        }

        else if (Number.equals(validate.substring(0, 1)) &&
                 validateCountryNumber.equals(validate.substring(1, 3))) {
            accessCode = accessNumber_PBX;

            String setString = validate;

            String deletePartOfString = setString.substring(3); // ta bort plast 0 - 3 ur strängen....

            String setStringTotal = setNumberNoll + deletePartOfString; // sätt ihop strängen setStringTotal

            callForward = setStringTotal;

            return callForward;

        } // Om numret startar med '+' OCH countryCode_PBX är falsk så gör om till '00'.

        else if (Number.equals(validate.substring(0, 1)) &&
                 !validateCountryNumber.equals(validate.substring(1, 3))) {
            accessCode = accessNumber_PBX;

            String setString = validate;

            String deletePartOfString = setString.substring(1); // ta bort plats 0 - 1 ur strängen....

            String setStringTotal = setNumber + deletePartOfString; // sätt ihop strängen setStringTotal

            callForward = setStringTotal;

            return callForward;

        } // Om numret inte liknar '+' så är det riktnummer + telefonnummer.

        if (!Number.equals(validate.substring(0, 1))) {

            accessCode = accessNumber_PBX;

            callForward = validate;

            return callForward;

        }

        return callForward;
    }


    public void commandAction(Command c, Displayable d) { // SÄTTER COMMAND-ACTION STARTAR TRÄDETS KOMMANDON (trådar)
        Thread th = new Thread(this);
        thCmd = c;
        th.start();
        if (d.equals(mainList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(mainList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // setpresence

                        Display.getDisplay(this).setCurrent(absentList);

                        break;

                    case 1: // call forward

                        Display.getDisplay(this).setCurrent(callForwardList);

                        break;

                    case 2: // voicemail

                        Thread voice_att = new Thread() {

                            public void run() {

                                try {
                                    if (getSwitchBoardType().equals("3")) {

                                        String start_mexon_01 = "a" + "," +
                                                switchBoardNumber_PBX + "," +
                                                voiceMailSwitchboard_PBX + "p" +
                                                "#6*" +
                                                extensionNumber_PBX + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println("Auto access 3 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex) {
                                } catch (InvalidRecordIDException ex) {
                                } catch (RecordStoreException ex) {
                                }

                                try {
                                    if (getSwitchBoardType().equals("4")) {

                                        String start_mexon_01 = "s" + "," +
                                                switchBoardNumber_PBX + "," +
                                                "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                voiceMailSwitchboard_PBX + "p" +
                                                "#6*" +
                                                extensionNumber_PBX + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                " PIN-Code access 4 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex1) {
                                } catch (InvalidRecordIDException ex1) {
                                } catch (RecordStoreException ex1) {
                                }

                            }
                        };
                        voice_att.start();

                        //Display.getDisplay(this).setCurrent(getResponseTextBox());

                        break
                                ;

                    case 3: // groups

                        Display.getDisplay(this).setCurrent(groupList);

                        break;

                    case 4: // Mex on

                        Thread a = new Thread() {

                            public void run() {

                                try {
                                    if (getSwitchBoardType().equals("3")) {

                                        String start_mexon_01 = "s," +
                                                switchBoardNumber_PBX + "," +
                                                accessNumber_PBX + ","
                                                + countryCode_PBX + "," +
                                                voiceMailOperator_PBX + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                "Auto access 3 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex) {
                                } catch (InvalidRecordIDException ex) {
                                } catch (RecordStoreException ex) {
                                }

                                try {
                                    if (getSwitchBoardType().equals("4")) {

                                        String start_mexon_01 = "s," +
                                                switchBoardNumber_PBX + "," +
                                                "*47" + extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                accessNumber_PBX + ","
                                                + countryCode_PBX + "," +
                                                voiceMailOperator_PBX + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                " PIN-Code access 4 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException
                                         ex1) {
                                } catch (InvalidRecordIDException ex1) {
                                } catch (RecordStoreException ex1) {
                                }

                            }
                        };
                        a.start();

                        break;

                    case 5: // Mex off

                        Thread b = new Thread() {


                            public void run() {

                                try {
                                    String close_mexoff = "e" + ",";

                                    sendRequest(close_mexoff);

                                    System.out.println(
                                            "Auto access 3 >> " +
                                            close_mexoff);
                                } catch (Exception ex) {
                                }
                            }
                        };
                        b.start();

                        break;

                    case 6: // minimaze
                        Thread minimise = new Thread() {


                            public void run() {

                                try {
                                    String close_mexoff = "m,";

                                    sendRequest(close_mexoff);

                                    System.out.println(
                                            "Auto access 3 >> " +
                                            close_mexoff);
                                } catch (Exception ex) {
                                }

                            }
                        };
                        minimise.start();

                        break;

                    }
                }

            }

        }
        if (d.equals(absentList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(absentList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Remove Presence

                        try {
                            if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                                Thread a_absentRemove = new Thread() {


                                    public void run() {

                                        String absent_remove = "h," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "500" + "#,";

                                        sendRequest(absent_remove);

                                        System.out.println(
                                                "Remove Presence >> " +
                                                absent_remove);

                                    }
                                };
                                a_absentRemove.start();

                            }
                        } catch (RecordStoreNotOpenException ex14) {
                        } catch (InvalidRecordIDException ex14) {
                        } catch (RecordStoreException ex14) {
                        }
                        try {
                            if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                                Thread a_absentRemove_pinCode = new Thread() {

                                    public void run() {

                                        String absent_remove = "h," +
                                                switchBoardNumber_PBX + ",*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "500" + "#,";

                                        sendRequest(absent_remove);

                                        System.out.println(
                                                "Skriver ut call_short >> " +
                                                absent_remove);

                                    }
                                };
                                a_absentRemove_pinCode.start();

                            }
                        } catch (RecordStoreNotOpenException ex15) {
                        } catch (InvalidRecordIDException ex15) {
                        } catch (RecordStoreException ex15) {
                        }
                        // Display.getDisplay(this).setCurrent(getResponseTextBox());
                        break
                                ;

                    case 1: // Will Return Soon

                        try {
                            if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                                Thread a_absentReturnSoon = new Thread() {


                                    public void run() {

                                        String absent_returnSoon = "h," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "501" + "#,";

                                        sendRequest(absent_returnSoon);

                                        System.out.println("Returns Soon >> " +
                                                absent_returnSoon);

                                    }
                                };
                                a_absentReturnSoon.start();

                            }
                        } catch (RecordStoreNotOpenException ex14) {
                        } catch (InvalidRecordIDException ex14) {
                        } catch (RecordStoreException ex14) {
                        }
                        try {
                            if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                                Thread a_absentReturnSoon_pinCode = new Thread() {


                                    public void run() {

                                        String absent_returnSoon = "h," +
                                                switchBoardNumber_PBX + ",*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "501" + "#,";

                                        sendRequest(absent_returnSoon);

                                        System.out.println("Returns Soon >> " +
                                                absent_returnSoon);

                                    }
                                };
                                a_absentReturnSoon_pinCode.start();

                            }
                        } catch (RecordStoreNotOpenException ex15) {
                        } catch (InvalidRecordIDException ex15) {
                        } catch (RecordStoreException ex15) {
                        }
                        // Display.getDisplay(this).setCurrent(getResponseTextBox());
                        break
                                ;

                    case 2: // // Gone Home

                        try {
                            if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                                Thread a_goneHome = new Thread() {


                                    public void run() {

                                        String absent_goneHome = "h," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "502" + "#,";

                                        sendRequest(absent_goneHome);

                                        System.out.println("Gone home >> " +
                                                absent_goneHome);

                                    }
                                };
                                a_goneHome.start();

                            }
                        } catch (RecordStoreNotOpenException ex14) {
                        } catch (InvalidRecordIDException ex14) {
                        } catch (RecordStoreException ex14) {
                        }
                        try {
                            if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                                Thread a_goneHome_pinCode = new Thread() {


                                    public void run() {

                                        String absent_goneHome = "h," +
                                                switchBoardNumber_PBX + ",*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "502" + "#,";

                                        sendRequest(absent_goneHome);

                                        System.out.println("Gone home >> " +
                                                absent_goneHome);

                                    }
                                };
                                a_goneHome_pinCode.start();

                            }
                        } catch (RecordStoreNotOpenException ex15) {
                        } catch (InvalidRecordIDException ex15) {
                        } catch (RecordStoreException ex15) {
                        }
                        break
                                ;

                    case 3: // At Ext

                        Display.getDisplay(this).setCurrent(getAtExtForm());

                        break;

                    case 4: // Back At

                        Display.getDisplay(this).setCurrent(getBackAtForm());

                        break;

                    case 5: // Out Until

                        Display.getDisplay(this).setCurrent(getOutUntilForm());

                        break;

                    case 6: // In A Meeting

                        try {
                            if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                                Thread a_inAMeeting = new Thread() {


                                    public void run() {

                                        String absent_inAMeeting = "h," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "506" + "#,";

                                        sendRequest(absent_inAMeeting);

                                        System.out.println("Meeting >> " +
                                                absent_inAMeeting);

                                    }
                                };
                                a_inAMeeting.start();

                            }
                        } catch (RecordStoreNotOpenException ex14) {
                        } catch (InvalidRecordIDException ex14) {
                        } catch (RecordStoreException ex14) {
                        }
                        try {
                            if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                                Thread a_inAMeeting_pinCode = new Thread() {


                                    public void run() {

                                        String absent_inAMeeting = "h," +
                                                switchBoardNumber_PBX + ",*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "506" + "#,";

                                        sendRequest(absent_inAMeeting);

                                        System.out.println("Meeting >> " +
                                                absent_inAMeeting);

                                    }
                                };
                                a_inAMeeting_pinCode.start();

                            }
                        } catch (RecordStoreNotOpenException ex15) {
                        } catch (InvalidRecordIDException ex15) {
                        } catch (RecordStoreException ex15) {
                        }
                        break
                                ;

                    case 7: // system attributs 1

                        try {
                            if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                                Thread a_SystemAtt_1 = new Thread() {


                                    public void run() {

                                        String absent_systemAtt_one = "h," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "507" + "#,";

                                        sendRequest(absent_systemAtt_one);

                                        System.out.println("System 1 >> " +
                                                absent_systemAtt_one);

                                    }
                                };
                                a_SystemAtt_1.start();

                            }
                        } catch (RecordStoreNotOpenException ex14) {
                        } catch (InvalidRecordIDException ex14) {
                        } catch (RecordStoreException ex14) {
                        }
                        try {
                            if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                                Thread a_SystemAtt_1_pinCode = new Thread() {


                                    public void run() {

                                        String absent_systemAtt_one = "h," +
                                                switchBoardNumber_PBX + ",*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "507" + "#,";

                                        sendRequest(absent_systemAtt_one);

                                        System.out.println("System 1 >> " +
                                                absent_systemAtt_one);

                                    }
                                };
                                a_SystemAtt_1_pinCode.start();

                            }
                        } catch (RecordStoreNotOpenException ex15) {
                        } catch (InvalidRecordIDException ex15) {
                        } catch (RecordStoreException ex15) {
                        }
                        break
                                ;

                    case 8: // system attributs 2

                        try {
                            if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                                Thread a_SystemAtt_2 = new Thread() {


                                    public void run() {

                                        String absent_systemAtt_two = "h," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "508" + "#,";

                                        sendRequest(absent_systemAtt_two);

                                        System.out.println("System 2 >> " +
                                                absent_systemAtt_two);

                                    }
                                };
                                a_SystemAtt_2.start();

                            }
                        } catch (RecordStoreNotOpenException ex14) {
                        } catch (InvalidRecordIDException ex14) {
                        } catch (RecordStoreException ex14) {
                        }
                        try {
                            if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                                Thread a_SystemAtt_2_pinCode = new Thread() {


                                    public void run() {

                                        String absent_systemAtt_two = "h," +
                                                switchBoardNumber_PBX + ",*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "508" + "#,";

                                        sendRequest(absent_systemAtt_two);

                                        System.out.println("System 2 >> " +
                                                absent_systemAtt_two);

                                    }
                                };
                                a_SystemAtt_2_pinCode.start();

                            }
                        } catch (RecordStoreNotOpenException ex15) {
                        } catch (InvalidRecordIDException ex15) {
                        } catch (RecordStoreException ex15) {
                        }
                        break
                                ;

                    case 9: // Personal Att

                        try {
                            if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                                Thread a_PersonalAtt = new Thread() {


                                    public void run() {

                                        String absent_personalAtt = "h," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "509" + "#,";

                                        sendRequest(absent_personalAtt);

                                        System.out.println("Personligt >> " +
                                                absent_personalAtt);

                                    }
                                };
                                a_PersonalAtt.start();

                            }
                        } catch (RecordStoreNotOpenException ex14) {
                        } catch (InvalidRecordIDException ex14) {
                        } catch (RecordStoreException ex14) {
                        }
                        try {
                            if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                                Thread a_PersonalAtt_pinCode = new Thread() {


                                    public void run() {

                                        String absent_personalAtt = "h," +
                                                switchBoardNumber_PBX + ",*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "509" + "#,";

                                        sendRequest(absent_personalAtt);

                                        System.out.println("Personligt >> " +
                                                absent_personalAtt);

                                    }
                                };
                                a_PersonalAtt_pinCode.start();

                            }
                        } catch (RecordStoreNotOpenException ex15) {
                        } catch (InvalidRecordIDException ex15) {
                        } catch (RecordStoreException ex15) {
                        }
                        break
                                ;

                    }
                }

            }

        }

        if (d.equals(callForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(callForwardList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        Display.getDisplay(this).setCurrent(allCallForwardList);

                        break;

                    case 1:

                        Display.getDisplay(this).setCurrent(
                                externCallForwardList);

                        break;

                    case 2:

                        Display.getDisplay(this).setCurrent(
                                internCallForwardList);

                        break;

                    }
                }

            }

        }

        if (d.equals(editSystemAbsentList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(editSystemAbsentList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        try {
                            Display.getDisplay(this).setCurrent(
                                    getEditSystemForm_1());
                        } catch (InvalidRecordIDException ex8) {
                        } catch (RecordStoreNotOpenException ex8) {
                        } catch (RecordStoreException ex8) {
                        }

                        break
                                ;

                    case 1:

                        try {
                            Display.getDisplay(this).setCurrent(
                                    getEditSystemForm_2());
                        } catch (InvalidRecordIDException ex9) {
                        } catch (RecordStoreNotOpenException ex9) {
                        } catch (RecordStoreException ex9) {
                        }

                        break
                                ;

                    case 2:

                        try {
                            Display.getDisplay(this).setCurrent(
                                    getEditPersonalForm());
                        } catch (InvalidRecordIDException ex10) {
                        } catch (RecordStoreNotOpenException ex10) {
                        } catch (RecordStoreException ex10) {
                        }

                        break
                                ;

                    }
                }

            }

        }

        if (d.equals(allCallForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(allCallForwardList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        Thread allCallForward_attOne = new Thread() {

                            public void run() {

                                try {
                                    if (getSwitchBoardType().equals("3")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "100" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println("Auto access 3 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex) {
                                } catch (InvalidRecordIDException ex) {
                                } catch (RecordStoreException ex) {
                                }

                                try {
                                    if (getSwitchBoardType().equals("4")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "100" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                " PIN-Code access 4 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex1) {
                                } catch (InvalidRecordIDException ex1) {
                                } catch (RecordStoreException ex1) {
                                }

                            }
                        };
                        allCallForward_attOne.start();

                        // Display.getDisplay(this).setCurrent(getResponseTextBox());

                        break
                                ;

                    case 1:

                        Thread allCallForward_attTwo = new Thread() {

                            public void run() {

                                try {
                                    if (getSwitchBoardType().equals("3")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "101" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println("Auto access 3 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex) {
                                } catch (InvalidRecordIDException ex) {
                                } catch (RecordStoreException ex) {
                                }

                                try {
                                    if (getSwitchBoardType().equals("4")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "101" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                " PIN-Code access 4 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex1) {
                                } catch (InvalidRecordIDException ex1) {
                                } catch (RecordStoreException ex1) {
                                }

                            }
                        };
                        allCallForward_attTwo.start();

                        break
                                ;

                    case 2:

                        Display.getDisplay(this).setCurrent(
                                getAllCallingForwardForm());

                        break;

                    case 3:

                        Display.getDisplay(this).setCurrent(
                                getAllBusyCallingForwardForm());

                        break;

                    case 4:

                        Display.getDisplay(this).setCurrent(
                                getAllNoAnswerCallingForwardForm());

                        break;

                    case 5:

                        Display.getDisplay(this).setCurrent(
                                getAllBusyNoAnswerCallingForwardForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(externCallForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(externCallForwardList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        Thread externCallForward_attOne = new Thread() {

                            public void run() {

                                try {
                                    if (getSwitchBoardType().equals("3")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "110" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println("Auto access 3 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex) {
                                } catch (InvalidRecordIDException ex) {
                                } catch (RecordStoreException ex) {
                                }

                                try {
                                    if (getSwitchBoardType().equals("4")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "110" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                " PIN-Code access 4 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex1) {
                                } catch (InvalidRecordIDException ex1) {
                                } catch (RecordStoreException ex1) {
                                }

                            }
                        };
                        externCallForward_attOne.start();


                        break
                                ;

                    case 1:

                        Thread externCallForward_attTwo = new Thread() {

                            public void run() {

                                try {
                                    if (getSwitchBoardType().equals("3")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "111" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println("Auto access 3 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex) {
                                } catch (InvalidRecordIDException ex) {
                                } catch (RecordStoreException ex) {
                                }

                                try {
                                    if (getSwitchBoardType().equals("4")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "111" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                " PIN-Code access 4 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex1) {
                                } catch (InvalidRecordIDException ex1) {
                                } catch (RecordStoreException ex1) {
                                }

                            }
                        };
                        externCallForward_attTwo.start();

                        break
                                ;

                    case 2:

                        Display.getDisplay(this).setCurrent(
                                getExternCallingForwardForm());

                        break;

                    case 3:

                        Display.getDisplay(this).setCurrent(
                                getExternBusyCallingForwardForm());

                        break;

                    case 4:

                        Display.getDisplay(this).setCurrent(
                                getExternNoAnswerCallingForwardForm());

                        break;

                    case 5:

                        Display.getDisplay(this).setCurrent(
                                getExternBusyNoAnswerCallingForwardForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(internCallForwardList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(internCallForwardList)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:

                        Thread internCallForward_attOne = new Thread() {

                            public void run() {

                                try {
                                    if (getSwitchBoardType().equals("3")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "120" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println("Auto access 3 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex) {
                                } catch (InvalidRecordIDException ex) {
                                } catch (RecordStoreException ex) {
                                }

                                try {
                                    if (getSwitchBoardType().equals("4")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "120" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                " PIN-Code access 4 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex1) {
                                } catch (InvalidRecordIDException ex1) {
                                } catch (RecordStoreException ex1) {
                                }

                            }
                        };
                        internCallForward_attOne.start();

                        break
                                ;

                    case 1:

                        Thread internCallForward_attTwo = new Thread() {

                            public void run() {

                                try {
                                    if (getSwitchBoardType().equals("3")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                precode_PBX + "121" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println("Auto access 3 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex) {
                                } catch (InvalidRecordIDException ex) {
                                } catch (RecordStoreException ex) {
                                }

                                try {
                                    if (getSwitchBoardType().equals("4")) {

                                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX + "," +
                                                "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX +
                                                precode_PBX + "121" + ",";

                                        sendRequest(start_mexon_01);

                                        System.out.println(
                                                " PIN-Code access 4 >> " +
                                                start_mexon_01);

                                    }
                                } catch (RecordStoreNotOpenException ex1) {
                                } catch (InvalidRecordIDException ex1) {
                                } catch (RecordStoreException ex1) {
                                }

                            }
                        };
                        internCallForward_attTwo.start();

                        break
                                ;

                    case 2:

                        Display.getDisplay(this).setCurrent(
                                getInternCallingForwardForm());

                        break;

                    case 3:

                        Display.getDisplay(this).setCurrent(
                                getInternBusyCallingForwardForm());

                        break;

                    case 4:

                        Display.getDisplay(this).setCurrent(
                                getInternNoAnswerCallingForwardForm());

                        break;

                    case 5:

                        Display.getDisplay(this).setCurrent(
                                getInternBusyNoAnswerCallingForwardForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(pbx_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(pbx_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0:

                        Display.getDisplay(this).setCurrent(pbx_List_type);

                        break
                                ;

                    case 1:
                        try {
                            Display.getDisplay(this).setCurrent(getPreEditForm());
                        } catch (InvalidRecordIDException ex13) {
                        } catch (RecordStoreNotOpenException ex13) {
                        } catch (RecordStoreException ex13) {
                        }
                        break
                                ;

                    case 2:
                        try {
                            Display.getDisplay(this).setCurrent(
                                    getVoiceEditForm());
                        } catch (InvalidRecordIDException ex13) {
                        } catch (RecordStoreNotOpenException ex13) {
                        } catch (RecordStoreException ex13) {
                        }

                        break
                                ;
                    case 3:

                        Display.getDisplay(this).setCurrent(getLanguageList());

                        break;

                    case 4:

                        Display.getDisplay(this).setCurrent(
                                getEditVoiceMessageForm());

                        break;

                    }
                }

            }

        }

        if (d.equals(pbx_List_type)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(pbx_List_type)) {
                    switch (((List) d).getSelectedIndex()) {
                    case 0:
                        pbx_auto = "3";

                        openRecStore();
                        setPbxAutoSettings(pbx_auto);
                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            System.out.println(
                                    getSwitchBoardType());
                        } catch (RecordStoreNotOpenException
                                 ex2) {
                        } catch (InvalidRecordIDException ex2) {
                        } catch (RecordStoreException ex2) {
                        }
                        Display.getDisplay(this).setCurrent(getEditSettingForm2());

                        break;

                    case 1:
                        pbx_pincode = "4";

                        openRecStore();
                        setPBXPincodeSettings(pbx_pincode);
                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException
                                 ex3) {
                        } catch (InvalidRecordIDException ex3) {
                        } catch (RecordStoreException ex3) {
                        }

                        try {
                            System.out.println(
                                    getSwitchBoardType());
                        } catch (RecordStoreNotOpenException
                                 ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }
                        Display.getDisplay(this).setCurrent(getEditSettingForm());
                        break;

                    }
                }

            }

        }

        //--------------------------------------------
        // getLogInTextBox() getLogoffTextBox()
        if (d.equals(groupList)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(groupList)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // inlogg alla grupper

                        System.out.println("Inlogg alla grupper");

                        logInAllGroups();

                        break
                                ;

                    case 1: // urlogg alla grupper

                        System.out.println("Urlogg alla grupper");

                        logOffAllGroups();

                        break
                                ;

                    case 2: // inlogg grupp

                        System.out.println("Inlogg grupp");
                        Display.getDisplay(this).setCurrent(getLoginGroupForm());

                        break
                                ;

                    case 3: // urlogg grupp

                        System.out.println("Urlogg grupp");
                        Display.getDisplay(this).setCurrent(getLogoffGroupForm());

                        break;

                    }
                }

            }

        }

        //--------------------------------------------
        if (d.equals(language_List)) {
            if (c == List.SELECT_COMMAND) {
                if (d.equals(language_List)) {
                    switch (((List) d).getSelectedIndex()) {

                    case 0: // Danish

                        lang_PBX = "0";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_DE);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_DE);
                        setPersonalAttribute(absentDefaultPersonalAtt_DE);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 1: // Dutch

                        lang_PBX = "1";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_DU);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_DU);
                        setPersonalAttribute(absentDefaultPersonalAtt_DU);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 2: // English

                        lang_PBX = "2";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_ENG);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_ENG);
                        setPersonalAttribute(absentDefaultPersonalAtt_ENG);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 3: // Finnish

                        lang_PBX = "3";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_FIN);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_FIN);
                        setPersonalAttribute(absentDefaultPersonalAtt_FIN);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 4: // French

                        lang_PBX = "4";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_FRA);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_FRA);
                        setPersonalAttribute(absentDefaultPersonalAtt_FRA);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 5: // German

                        lang_PBX = "5";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_GE);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_GE);
                        setPersonalAttribute(absentDefaultPersonalAtt_GE);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 6: // Norwegian

                        lang_PBX = "6";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_NO);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_NO);
                        setPersonalAttribute(absentDefaultPersonalAtt_NO);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 7: // Italian

                        lang_PBX = "7";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_IT);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_IT);
                        setPersonalAttribute(absentDefaultPersonalAtt_IT);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 8: // Spanish

                        lang_PBX = "8";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_SP);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_SP);
                        setPersonalAttribute(absentDefaultPersonalAtt_SP);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                    case 9: // Swedish
                        lang_PBX = "9";

                        openRecStore();
                        setLanguage(lang_PBX);
                        setSystemAttributeONE(absentDefaultSystemAttOne_SE);
                        setSystemAttributeTWO(absentDeafaultSystemAttTwo_SE);
                        setPersonalAttribute(absentDefaultPersonalAtt_SE);

                        closeRecStore();
                        try {
                            upDateDataStore();
                        } catch (RecordStoreNotOpenException ex4) {
                        } catch (InvalidRecordIDException ex4) {
                        } catch (RecordStoreException ex4) {
                        }

                        try {
                            setDataStore();
                        } catch (InvalidRecordIDException ex6) {
                        } catch (RecordStoreNotOpenException ex6) {
                        } catch (RecordStoreException ex6) {
                        }

                        Display.getDisplay(this).setCurrent(getCountryForm());

                        break;

                        /* case 10:
                             break;

                         case 11:

                             break;

                         case 12:

                             break;

                         case 13:


                             break;

                         case 14:

                             break;
                         case 15:

                             break;

                         case 16:

                             break;*/


                    }
                }

            }

        }

    }

    public void run() {
        try {
            if (thCmd.getCommandType() == Command.EXIT) {
                notifyDestroyed();

            } else if (thCmd == AboutCommand) {

                backCommand = new Command(genDefaultBack_DEF, Command.OK,
                                          2);

                Displayable k = new AboutUs();
                Display.getDisplay(this).setCurrent(k);
                //k.addCommand(systemCommand);
                k.addCommand(backCommand);
                k.setCommandListener(this); // settingsListNextCommand

            } else if (thCmd == aboutMobismaCommand) {

                setDataStore();
                upDateDataStore();

                Displayable k = new ServerNumber(switchBoardNumber_PBX,
                                                 /*, IMEI,
                                                 star,*/
                                                 accessNumber_PBX,
                                                 ViewDateString);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(goBackCommand);
                // k.addCommand(urlCommand);
                k.addCommand(AboutCommand);
                k.addCommand(helpCommand);
                k.setCommandListener(this);

            } else if (thCmd == helpCommand) { // Kommandot 'Om Tv-Moble' hör till huvudfönstret listan

                backCommand = new Command(genDefaultBack_DEF, Command.OK,
                                          2);

                Displayable k = new HelpInfo();
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(backCommand);
                k.setCommandListener(this);

            } else if (thCmd == systemCommand) {

                Display.getDisplay(this).setCurrent(getDebugForm());

            } else if (thCmd == saveEditSystemCommand_1) {

                if (editTextSystemTextField_1.getString().equals("")) {

                    //Display.getDisplay(this).setCurrent(absentList);
                    displayAlert(NAMEERROR,
                                 alertDefaultCouldntAddChangesEmtpyField_DEF,
                                 getEditSystemForm_1());

                } else if (!editTextSystemTextField_1.getString().equals("")) {

                    openRecStore();
                    setSystemText_1_Attribute();
                    this.systemText_1_Attribute = getSystemText_1_Attribute();
                    closeRecStore();
                    Display.getDisplay(this).setCurrent(alertEditSettings,
                            absentList);

                }

            } else if (thCmd == saveEditSystemCommand_2) { // tillhör delete textbox testet.

                if (editTextSystemTextField_2.getString().equals("")) {

                    //Display.getDisplay(this).setCurrent(absentList);
                    displayAlert(NAMEERROR,
                                 alertDefaultCouldntAddChangesEmtpyField_DEF,
                                 getEditSystemForm_2());

                } else if (!editTextSystemTextField_2.getString().equals("")) {

                    openRecStore();
                    setSystemText_2_Attribute();
                    this.systemText_2_Attribute = getSystemText_2_Attribute();
                    closeRecStore();
                    Display.getDisplay(this).setCurrent(alertEditSettings,
                            absentList);

                }

            } else if (thCmd == saveEditPersonalCommand) { // tillhör delete textbox testet.

                if (editTextPersonalTextField.getString().equals("")) {

                    //Display.getDisplay(this).setCurrent(absentList);
                    displayAlert(NAMEERROR,
                                 alertDefaultCouldntAddChangesEmtpyField_DEF,
                                 getEditPersonalForm());

                } else if (!editTextPersonalTextField.getString().equals("")) {

                    openRecStore();
                    setPersonalText_Attribute();
                    this.personalText_Attribute = getPersonalText_Attribute();
                    closeRecStore();
                    Display.getDisplay(this).setCurrent(alertEditSettings,
                            absentList);

                }

            } else if (thCmd == groupBackCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == confirmExitYESCommand) {

                notifyDestroyed();

            } else if (thCmd == confirmExitNOCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == ExitCommandMainList) {

                Thread alertEND = new Thread() {

                    public void run() {

                        try {
                            String alertEND = "f,";

                            sendRequest(alertEND);

                            System.out.println("Alert END >> " + alertEND);
                        } catch (Exception ex) {
                        }

                    }
                };
                alertEND.start();

            }

            else if (thCmd == internCallForwardDialCommand) {

                Thread internCallForward_attThree = new Thread() {

                    String setCallString = internCallingForwardTextField.
                                           getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "122" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getInternCallingForwardForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }
                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "122" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }
                    }
                };
                internCallForward_attThree.start();
                internCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == internBusyCallForwardDialCommand) {

                Thread internCallForward_attFour = new Thread() {

                    String setCallString = internBusyCallingForwardTextField.
                                           getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "123" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "123" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                internCallForward_attFour.start();
                internBusyCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == internNoAnswerCallForwardDialCommand) {

                Thread internCallForward_attFive = new Thread() {

                    String setCallString =
                            internNoAnswerCallingForwardTextField.getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "124" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "124" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                internCallForward_attFive.start();
                internNoAnswerCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == internBusyNoAnswerCallForwardDialCommand) {

                Thread internCallForward_attSix = new Thread() {

                    String setCallString =
                            internBusyNoAnswerCallingForwardTextField.getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);


                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX + "125" + accessCode +
                                        dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "125" +
                                        accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                internCallForward_attSix.start();
                internBusyNoAnswerCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == externCallForwardDialCommand) {

                Thread externCallForward_attThree = new Thread() {

                    String setCallString = externCallingForwardTextField.
                                           getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);


                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "112" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "112" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }
                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                externCallForward_attThree.start();
                externCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == externBusyCallForwardDialCommand) {

                Thread externCallForward_attFour = new Thread() {

                    String setCallString = externBusyCallingForwardTextField.
                                           getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "113" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "113" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                externCallForward_attFour.start();
                externBusyCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == externNoAnswerCallForwardDialCommand) {

                Thread externCallForward_attFive = new Thread() {

                    String setCallString =
                            externNoAnswerCallingForwardTextField.getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "114" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "114" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                externCallForward_attFive.start();
                externNoAnswerCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == externBusyNoAnswerCallForwardDialCommand) {

                Thread externCallForward_attSix = new Thread() {

                    String setCallString =
                            externBusyNoAnswerCallingForwardTextField.getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "115" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "115" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                externCallForward_attSix.start();
                externBusyNoAnswerCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == allCallForwardDialCommand) {

                Thread allCallForward_attThree = new Thread() {

                    String setCallString = allCallingForwardTextField.getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "102" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "102" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                allCallForward_attThree.start();
                allCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == allBusyCallForwardDialCommand) {

                Thread allCallForward_attFour = new Thread() {

                    String setCallString = allBusyCallingForwardTextField.
                                           getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "103" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "103" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                allCallForward_attFour.start();
                allBusyCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == allNoAnswerCallForwardDialCommand) {

                Thread allCallForward_attFive = new Thread() {

                    String setCallString = allNoAnswerCallingForwardTextField.
                                           getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);

                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "104" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "104" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }
                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                allCallForward_attFive.start();
                allNoAnswerCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == allBusyNoAnswerCallForwardDialCommand) {

                Thread allCallForward_attSix = new Thread() {


                    String setCallString =
                            allBusyNoAnswerCallingForwardTextField.getString();
                    String dialForwardString = checkCallForwardNumber(
                            setCallString);


                    public void run() {

                        try {
                            if (getSwitchBoardType().equals("3")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX +
                                        "105" + accessCode + dialForwardString +
                                        "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }

                            }
                        } catch (RecordStoreNotOpenException ex) {
                        } catch (InvalidRecordIDException ex) {
                        } catch (RecordStoreException ex) {
                        }

                        try {
                            if (getSwitchBoardType().equals("4")) {

                                String start_mexon_01 = "h" + "," +
                                        switchBoardNumber_PBX + "," + "*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "105" +
                                        accessCode + dialForwardString + "#,";

                                if (dialForwardString.equals("")) {

                                    getLoginGroupForm();

                                }
                                if (!dialForwardString.equals("")) {

                                    sendRequest(start_mexon_01);

                                    System.out.println("Auto access 3 >> " +
                                            start_mexon_01);

                                }
                            }
                        } catch (RecordStoreNotOpenException ex1) {
                        } catch (InvalidRecordIDException ex1) {
                        } catch (RecordStoreException ex1) {
                        }

                    }
                };
                allCallForward_attSix.start();
                allBusyNoAnswerCallingForwardTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == responseCancelCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == keyCodeCancelCommand) {

                Display.getDisplay(this).setCurrent(getResponseTextBox());

            } else if (thCmd == keyCodeSaveCommand) {

                openRecStore();
                setKeyCode();
                closeRecStore();
                upDateDataStore();
                setDataStore();
                startApp();

                Display.getDisplay(this).setCurrent(alertEditSettings, mainList);

            } else if (thCmd == keyCodeCommand) {

                Display.getDisplay(this).setCurrent(getKeyCodeForm());

            } else if (thCmd == clearCommand) {

                response_textbox.setString("");

            } else if (thCmd == preEditBackCommand) {

                Display.getDisplay(this).setCurrent(pbx_List);

            } else if (thCmd == debugCancelCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == preEditCancelCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == voiceEditBackcommand) {

                Display.getDisplay(this).setCurrent(pbx_List);

            } else if (thCmd == voiceEditCancelCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == internBusyNoAnswerCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(internCallForwardList);

            } else if (thCmd == internNoAnswerCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(internCallForwardList);

            } else if (thCmd == internBusyCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(internCallForwardList);

            } else if (thCmd == internCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(internCallForwardList);

            } else if (thCmd == externBusyNoAnswerCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(externCallForwardList);

            } else if (thCmd == externNoAnswerCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(externCallForwardList);

            } else if (thCmd == externBusyCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(externCallForwardList);

            } else if (thCmd == externCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(externCallForwardList);

            } else if (thCmd == allBusyCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(allCallForwardList);

            } else if (thCmd == allNoAnswerCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(allCallForwardList);

            } else if (thCmd == allBusyNoAnswerCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(allCallForwardList);

            } else if (thCmd == allCallForwardBackCommand) {

                Display.getDisplay(this).setCurrent(allCallForwardList);

            } else if (thCmd == backInternCallForwardCommand) {

                Display.getDisplay(this).setCurrent(callForwardList);

            } else if (thCmd == backExternCallForwardCommand) {

                Display.getDisplay(this).setCurrent(callForwardList);

            } else if (thCmd == backCallForwardCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == backAllCallForwardCommand) {

                Display.getDisplay(this).setCurrent(callForwardList);

            } else if (thCmd == editAbsentSystemCommand) {

                Display.getDisplay(this).setCurrent(editSystemAbsentList);

            } else if (thCmd == loginGroupDialCommand) {

                logInSpecificGroups();

            }

            else if (thCmd == logoffGroupDialCommand) {

                logOffSpecificGroups();

            }

            else if (thCmd == cancelEditSystemCommand_1) {

                Display.getDisplay(this).setCurrent(absentList);

            } else if (thCmd == backEditAbsentSystemCommand) {

                Display.getDisplay(this).setCurrent(absentList);

            } else if (thCmd == cancelEditSystemCommand_2) {

                Display.getDisplay(this).setCurrent(absentList);

            } else if (thCmd == cancelEditPersonalCommand) {

                Display.getDisplay(this).setCurrent(absentList);

            } else if (thCmd == voiceMessageSaveCommand) { // Kommandot 'Spara' hör till editSetting-Form

                openRecStore();
                setVoiceMessage();
                closeRecStore();
                upDateDataStore();
                setDataStore();
                startApp();

                Display.getDisplay(this).setCurrent(alertEditSettings, mainList);

            } else if (thCmd == voiceMessageBackCommand) {

                Display.getDisplay(this).setCurrent(pbx_List);

            } else if (thCmd == voiceMessageCancelCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == loginGroupBackCommand) {

                Display.getDisplay(this).setCurrent(groupList);

            } else if (thCmd == logoffGroupBackCommand) {

                Display.getDisplay(this).setCurrent(groupList);

            } else if (thCmd == voiceMailBackCommand) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == propertiesCommand) { // Kommandot 'Redigera' hör till setting-Form

                Display.getDisplay(this).setCurrent(getSettingsList());

            } else if (thCmd == settingsListCancelCommand) { // Kommandot 'Redigera' hör till setting-Form

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == settingsPBXListCancelCommand) { // Kommandot 'Redigera' hör till setting-Form

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == Pbx_List_Type_backCommand) {

                Display.getDisplay(this).setCurrent(pbx_List);

            } else if (thCmd == atExtBackCommand) {

                Display.getDisplay(this).setCurrent(absentList);

            } else if (thCmd == meny) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == ExitCommandAbsentList) {

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == debugOKCommand) {

                String debugon = "*35#";
                String Controll_debugon = debugTextField.getString();

                if (Controll_debugon.equals(debugon)) {

                    Display.getDisplay(this).setCurrent(getResponseTextBox());
                    regFromTextFile();
                    debugTextField.setString("");

                }
                if (!Controll_debugon.equals(debugon)) {

                    Display.getDisplay(this).setCurrent(getDebugForm());
                    debugTextField.setString("");

                }

            } else if (thCmd == responseUpdateCommand) {

                debugTextField.setString("");
                Display.getDisplay(this).setCurrent(getResponseTextBox());
                regFromTextFile();

            }

            else if (thCmd == responseDebugOffCommand) {

                Thread d1 = new Thread() {


                    public void run() {

                        try {
                            String stop_debug = "d,0,";

                            sendRequest(stop_debug);

                            System.out.println("Starta debug >> " + stop_debug);
                        } catch (Exception ex) {
                        }

                    }
                };
                d1.start();

            }

            else if (thCmd == responseDebugOnCommand) {

                Thread c1 = new Thread() {

                    public void run() {

                        try {
                            String start_debug = "d,1,";

                            sendRequest(start_debug);

                            System.out.println("Starta debug >> " + start_debug);
                        } catch (Exception ex) {
                        }

                    }
                };
                c1.start();

            }

            else if (thCmd == countryBackCommand) {

                Display.getDisplay(this).setCurrent(language_List);

            } else if (thCmd == settingsCommand) {

                setDataStore();
                upDateDataStore();

                Displayable k = new ServerNumber(switchBoardNumber_PBX,
                                                 /*, IMEI,
                                                 star,*/
                                                 accessNumber_PBX,
                                                 ViewDateString);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(goBackCommand);
                k.addCommand(AboutCommand);
                k.addCommand(helpCommand);
                k.setCommandListener(this);

            } else if (thCmd == editSettingBackCommand) { // Kommandot 'Tillbaka' hör till editSetting-Form

                Display.getDisplay(this).setCurrent(pbx_List_type);

            } else if (thCmd == editSettingBackCommand2) { // Kommandot 'Tillbaka' hör till editSetting-Form

                Display.getDisplay(this).setCurrent(pbx_List_type);

            } else if (thCmd == backCommand) { // Kommandot 'Tillbaka' hör till about-formen

                setDataStore();
                upDateDataStore();

                Displayable k = new ServerNumber(switchBoardNumber_PBX,
                                                 /*, IMEI,
                                                 star,*/
                                                 accessNumber_PBX,
                                                 ViewDateString);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(goBackCommand);
                k.addCommand(AboutCommand);
                k.addCommand(helpCommand);
                k.setCommandListener(this);

            } else if (thCmd == backCommand) { // Kommandot 'Tillbaka' hör till about-formen

                setDataStore();
                upDateDataStore();

                Displayable k = new ServerNumber(switchBoardNumber_PBX,
                                                 /*, IMEI,
                                                 star,*/
                                                 accessNumber_PBX,
                                                 ViewDateString);
                Display.getDisplay(this).setCurrent(k);
                k.addCommand(goBackCommand);
                k.addCommand(AboutCommand);
                k.addCommand(helpCommand);
                k.setCommandListener(this);

            } else if (thCmd == countryCancelCommand) { // Kommandot 'Logga Ut' hör till setting-Form

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == editSettingCancelCommand) { // Kommandot 'Logga Ut' hör till setting-Form

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == editSettingCancelCommand2) { // Kommandot 'Logga Ut' hör till setting-Form

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == languageListCancelCommand) { // Kommandot 'Logga Ut' hör till setting-Form

                Display.getDisplay(this).setCurrent(mainList);

            } else if (thCmd == editSettingSaveCommand) { // Kommandot 'Spara' hör till editSetting-Form

                openRecStore();
                setAccessNumber();
                setSwitchBoardNumber();
                setExtensionNumber();
                setPinCodeNumber();
                closeRecStore();
                upDateDataStore();
                setDataStore();
                startApp();

                Display.getDisplay(this).setCurrent(alertEditSettings,
                        mainList);

            } else if (thCmd == editSettingSaveCommand2) { // Kommandot 'Spara' hör till editSetting-Form

                openRecStore();
                setAccessNumber2();
                setSwitchBoardNumber2();
                closeRecStore();
                upDateDataStore();
                setDataStore();
                startApp();

                Display.getDisplay(this).setCurrent(alertEditSettings,
                        mainList);

            } else if (thCmd == countrySaveCommand) { // Kommandot 'Spara' hör till editSetting-Form

                openRecStore();
                setLanguageCallNumbers();
                closeRecStore();
                upDateDataStore();
                setDataStore();
                startApp();

                Display.getDisplay(this).setCurrent(alertRestarting, mainList);

            } else if (thCmd == preEditSaveCommand) { // Kommandot 'Spara' hör till editSetting-Form

                openRecStore();
                setPreEditCode();
                closeRecStore();
                upDateDataStore();
                setDataStore();
                startApp();

                Display.getDisplay(this).setCurrent(alertEditSettings, mainList);

            } else if (thCmd == voiceEditSaveCommand) { // Kommandot 'Spara' hör till editSetting-Form

                openRecStore();
                setVoiceEditCode();
                setVoiceExtensionNumber();
                closeRecStore();
                upDateDataStore();
                setDataStore();
                startApp();

                Display.getDisplay(this).setCurrent(alertEditSettings, mainList);

            } else if (thCmd == minimazeCommand) {

                Display.getDisplay(this).setCurrent(null);

            }

            else if (thCmd == outUntilBackCommand) {

                Display.getDisplay(this).setCurrent(absentList);

            }

            else if (thCmd == backAtCommand) {

                Display.getDisplay(this).setCurrent(absentList);

            } else if (thCmd == outUntilDialCommand) { // Out until
                try {
                    if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                        Thread a_OutUntil = new Thread() {

                            String setOutUntil = outUntilTextField.getString();

                            public void run() {

                                String absent_OutUntil = "h," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX + "505" + setOutUntil +
                                        "#,";

                                if (setOutUntil.equals("")) {

                                    getBackAtForm();

                                }
                                if (!setOutUntil.equals("")) {

                                    sendRequest(absent_OutUntil);

                                    System.out.println("Auto access 3 >> " +
                                            absent_OutUntil);

                                }

                            }
                        };
                        a_OutUntil.start();

                    }
                } catch (RecordStoreNotOpenException ex14) {
                } catch (InvalidRecordIDException ex14) {
                } catch (RecordStoreException ex14) {
                }
                try {
                    if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                        Thread a_OutUntil_pinCode = new Thread() {

                            String setOutUntil = outUntilTextField.getString();

                            public void run() {

                                String absent_OutUntil = "h," +
                                        switchBoardNumber_PBX + ",*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "505" + setOutUntil +
                                        "#,";

                                if (setOutUntil.equals("")) {

                                    getBackAtForm();

                                }
                                if (!setOutUntil.equals("")) {

                                    sendRequest(absent_OutUntil);

                                    System.out.println("Auto access 3 >> " +
                                            absent_OutUntil);

                                }

                            }
                        };
                        a_OutUntil_pinCode.start();

                    }
                } catch (RecordStoreNotOpenException ex15) {
                } catch (InvalidRecordIDException ex15) {
                } catch (RecordStoreException ex15) {
                }

                outUntilTextField.setString("");

                // Display.getDisplay(this).setCurrent(getResponseTextBox());
            }

            else if (thCmd == backAtDialCommand) { // Back At

                try {
                    if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                        Thread a_BackAt = new Thread() {

                            String setBackAt = backAtTextField.getString();

                            public void run() {

                                String absent_BackAt = "h," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX + "504" + setBackAt + "#,";

                                if (setBackAt.equals("")) {

                                    getBackAtForm();

                                }
                                if (!setBackAt.equals("")) {

                                    sendRequest(absent_BackAt);

                                    System.out.println("Auto access 3 >> " +
                                            absent_BackAt);

                                }

                            }
                        };
                        a_BackAt.start();

                    }
                } catch (RecordStoreNotOpenException ex14) {
                } catch (InvalidRecordIDException ex14) {
                } catch (RecordStoreException ex14) {
                }
                try {
                    if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                        Thread a_BackAt_pinCode = new Thread() {

                            String setBackAt = backAtTextField.getString();

                            public void run() {

                                String absent_BackAt = "h," +
                                        switchBoardNumber_PBX +
                                        ",*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "504" + setBackAt +
                                        "#,";

                                if (setBackAt.equals("")) {

                                    getBackAtForm();

                                }
                                if (!setBackAt.equals("")) {

                                    sendRequest(absent_BackAt);

                                    System.out.println("Auto access 3 >> " +
                                            absent_BackAt);

                                }

                            }
                        };
                        a_BackAt_pinCode.start();

                    }
                } catch (RecordStoreNotOpenException ex15) {
                } catch (InvalidRecordIDException ex15) {
                } catch (RecordStoreException ex15) {
                }
                backAtTextField.setString("");
                // Display.getDisplay(this).setCurrent(getResponseTextBox());
            }

            else if (thCmd == absentDialCommand) { // At extension

                try {
                    if (getSwitchBoardType().equals("3")) { // pbx_auto växeltyp

                        Thread a_AtExt = new Thread() {

                            String setAtExtension = atExtTextField.getString();


                            public void run() {

                                String absent_AtExt = "h," +
                                        switchBoardNumber_PBX + "," +
                                        precode_PBX + "503" + setAtExtension +
                                        "#,";

                                if (setAtExtension.equals("")) {

                                    getAtExtForm();

                                }
                                if (!setAtExtension.equals("")) {

                                    sendRequest(absent_AtExt);

                                    System.out.println("Auto access 3 >> " +
                                            absent_AtExt);

                                }

                            }
                        };
                        a_AtExt.start();

                    }
                } catch (RecordStoreNotOpenException ex14) {
                } catch (InvalidRecordIDException ex14) {
                } catch (RecordStoreException ex14) {
                }
                try {
                    if (getSwitchBoardType().equals("4")) { // PBX_Pincode växeltyp

                        Thread a_AtExt_pinCode = new Thread() {

                            String setAtExtension = atExtTextField.getString();


                            public void run() {

                                String absent_AtExt = "h," +
                                        switchBoardNumber_PBX +
                                        ",*47" +
                                        extensionNumber_PBX + pinCodeNumber_PBX +
                                        precode_PBX + "503" + setAtExtension +
                                        "#,";

                                if (setAtExtension.equals("")) {

                                    getAtExtForm();

                                }
                                if (!setAtExtension.equals("")) {

                                    sendRequest(absent_AtExt);

                                    System.out.println("Auto access 3 >> " +
                                            absent_AtExt);

                                }

                            }
                        };
                        a_AtExt_pinCode.start();

                    }
                } catch (RecordStoreNotOpenException ex15) {
                } catch (InvalidRecordIDException ex15) {
                } catch (RecordStoreException ex15) {
                }

                atExtTextField.setString("");

            } else if (thCmd.getCommandType() == Command.BACK) { // Kommandot 'Tillbaka' hör till about-formen

                Display.getDisplay(this).setCurrent(mainList);
            }

        } catch (Exception ex) {
        }
    }

    public void logOffAllGroups() {

        Thread logOffAllGroups = new Thread() {

            public void run() {

                try {
                    if (getSwitchBoardType().equals("3")) {

                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX +
                                                "," + precode_PBX + "360*" +
                                                ",";

                        sendRequest(start_mexon_01);

                        System.out.println("Auto access 3 >> " +
                                           start_mexon_01);

                    }
                } catch (RecordStoreNotOpenException ex) {
                } catch (InvalidRecordIDException ex) {
                } catch (RecordStoreException ex) {
                }

                try {
                    if (getSwitchBoardType().equals("4")) {

                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX +
                                                "," + "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX + precode_PBX +
                                                "360*" + ",";

                        sendRequest(start_mexon_01);

                        System.out.println(
                                " PIN-Code access 4 >> " +
                                start_mexon_01);

                    }
                } catch (RecordStoreNotOpenException ex1) {
                } catch (InvalidRecordIDException ex1) {
                } catch (RecordStoreException ex1) {
                }

            }
        };
        logOffAllGroups.start();

        // Display.getDisplay(this).setCurrent(getResponseTextBox());

    }

    public void logInAllGroups() {

        Thread logInAllGroups = new Thread() {

            public void run() {

                try {
                    if (getSwitchBoardType().equals("3")) {

                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX +
                                                "," + precode_PBX + "361*" +
                                                ",";

                        sendRequest(start_mexon_01);

                        System.out.println("Auto access 3 >> " +
                                           start_mexon_01);

                    }
                } catch (RecordStoreNotOpenException ex) {
                } catch (InvalidRecordIDException ex) {
                } catch (RecordStoreException ex) {
                }

                try {
                    if (getSwitchBoardType().equals("4")) {

                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX +
                                                "," + "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX + precode_PBX +
                                                "361*" + ",";

                        sendRequest(start_mexon_01);

                        System.out.println(
                                " PIN-Code access 4 >> " +
                                start_mexon_01);

                    }
                } catch (RecordStoreNotOpenException ex1) {
                } catch (InvalidRecordIDException ex1) {
                } catch (RecordStoreException ex1) {
                }

            }
        };
        logInAllGroups.start();

        // Display.getDisplay(this).setCurrent(getResponseTextBox());
    }

    public void logOffSpecificGroups() {

        Thread logOutSpecificGroups = new Thread() {

            String setSpeficGroups = logoffGroupTextField.getString();

            public void run() {

                try {
                    if (getSwitchBoardType().equals("3")) {

                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX +
                                                "," +
                                                precode_PBX + "360" +
                                                setSpeficGroups +
                                                ",";

                        if (setSpeficGroups.equals("")) {

                            getLoginGroupForm();

                        }
                        if (!setSpeficGroups.equals("")) {

                            sendRequest(start_mexon_01);

                            System.out.println("Auto access 3 >> " +
                                               start_mexon_01);

                        }

                    }
                } catch (RecordStoreNotOpenException ex) {
                } catch (InvalidRecordIDException ex) {
                } catch (RecordStoreException ex) {
                }

                try {
                    if (getSwitchBoardType().equals("4")) {

                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX +
                                                "," + "*47" +
                                                extensionNumber_PBX +
                                                pinCodeNumber_PBX + precode_PBX +
                                                "360" + setSpeficGroups + ",";

                        if (setSpeficGroups.equals("")) {

                            getLoginGroupForm();

                        }
                        if (!setSpeficGroups.equals("")) {

                            sendRequest(start_mexon_01);

                            System.out.println("Auto access 3 >> " +
                                               start_mexon_01);

                        }

                    }
                } catch (RecordStoreNotOpenException ex1) {
                } catch (InvalidRecordIDException ex1) {
                } catch (RecordStoreException ex1) {
                }

            }
        };
        logOutSpecificGroups.start();

        // Display.getDisplay(this).setCurrent(getResponseTextBox());

        logoffGroupTextField.setString("");
    }

    public void logInSpecificGroups() {

        Thread logInSpecificGroups = new Thread() {

            String setSpeficGroups = loginGroupTextField.getString();

            public void run() {

                try {
                    if (getSwitchBoardType().equals("3")) {

                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX +
                                                "," + precode_PBX + "361" +
                                                setSpeficGroups + ",";

                        if (setSpeficGroups.equals("")) {

                            getLoginGroupForm();

                        }
                        if (!setSpeficGroups.equals("")) {

                            sendRequest(start_mexon_01);

                            System.out.println("Auto access 3 >> " +
                                               start_mexon_01);

                        }
                    }
                } catch (RecordStoreNotOpenException ex) {
                } catch (InvalidRecordIDException ex) {
                } catch (RecordStoreException ex) {
                }

                try {
                    if (getSwitchBoardType().equals("4")) {

                        if (setSpeficGroups.equals("")) {

                            getLoginGroupForm();

                        }
                        String start_mexon_01 = "h" + "," +
                                                switchBoardNumber_PBX +
                                                "," +
                                                "*47" + extensionNumber_PBX +
                                                pinCodeNumber_PBX + precode_PBX +
                                                "361" +
                                                setSpeficGroups + ",";

                        if (setSpeficGroups.equals("")) {

                            getLoginGroupForm();

                        }
                        if (!setSpeficGroups.equals("")) {

                            sendRequest(start_mexon_01);

                            System.out.println("Auto access 3 >> " +
                                               start_mexon_01);

                        }

                    }
                } catch (RecordStoreNotOpenException ex1) {
                } catch (InvalidRecordIDException ex1) {
                } catch (RecordStoreException ex1) {
                }

            }
        };
        logInSpecificGroups.start();

        // Display.getDisplay(this).setCurrent(getResponseTextBox());

        loginGroupTextField.setString("");
    }

    public String getTime() {

        String stringMinutes;

        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int year = cal.get(Calendar.YEAR);
        int mounth = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        String sub = year + "";

        String years = sub.substring(2);

        if (minute == 0 || minute == 1 || minute == 2 || minute == 3 ||
            minute == 4
            || minute == 5 || minute == 6 || minute == 7
            || minute == 8 || minute == 9) {

            stringMinutes = Integer.toString(minute);

            String subMinutes = "0";

            stringMinutes = subMinutes + stringMinutes;

            String time = hour + "." + stringMinutes + " " + day + "/" + mounth +
                          "-" + years;

            System.out.println("klockan är >> " + time);

            return time;

        }

        String time = hour + "." + minute + " " + day + "/" + mounth + "-" +
                      years;

        System.out.println("klockan är >> " + time);

        return time;

    }


    //------------ D A T A - B A S - R M S -----------------------------------

    public Form getEditSettingForm() { // METODEN RETURNERAR FORMEN FÖR EDITSETTINGS I EGENSKAPER

        editSettingForm.deleteAll();
        openRecStore();
        accessNumbers.setString(accessNumber_PBX);
        editSettingForm.append(accessNumbers);
        editSwitchBoardNumber.setString(switchBoardNumber_PBX);
        editSettingForm.append(editSwitchBoardNumber);
        editExtensionNumber.setString(extensionNumber_PBX);
        editSettingForm.append(editExtensionNumber);
        editPinCodeNumber.setString(pinCodeNumber_PBX);
        editSettingForm.append(editPinCodeNumber);
        closeRecStore();

        return editSettingForm;
    }

    public Form getEditSettingForm2() { // METODEN RETURNERAR FORMEN FÖR EDITSETTINGS I EGENSKAPER

        editSettingForm2.deleteAll();
        openRecStore();
        accessNumbers2.setString(accessNumber_PBX);
        editSettingForm2.append(accessNumbers2);
        editSwitchBoardNumber2.setString(switchBoardNumber_PBX);
        editSettingForm2.append(editSwitchBoardNumber2);

        closeRecStore();

        return editSettingForm2;
    }


    public Form getCountryForm() { // METODEN RETURNERAR FORMEN FÖR EDITSETTINGS I EGENSKAPER

        countryForm.deleteAll();
        openRecStore();
        try {
            countryField.setString(countryCode_PBX);
        } catch (Exception ex) {
        }
        countryForm.append(countryField);
        closeRecStore();

        return countryForm;
    }


    // --- SET-metoder ------



    public void setDateNumber() {

        try {
            recStore.setRecord(3, dateNumber.getString().getBytes(), 0,
                               dateNumber.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setVoiceMessage() {

        try {
            recStore.setRecord(20, voiceMessageTextField.getString().getBytes(),
                               0,
                               voiceMessageTextField.getString().length());
        } catch (Exception e) {
            // ALERT
        }

    }


    public void testSet() {

        try {
            recStore.setRecord(4, MEXOn.getBytes(),
                               0,
                               MEXOn.length());

        } catch (Exception e) {
            // ALERT
        }

    }

    public void setAccessNumber() {

        try {
            recStore.setRecord(4, accessNumbers.getString().getBytes(), 0,
                               accessNumbers.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setAccessNumber2() {

        try {
            recStore.setRecord(4, accessNumbers2.getString().getBytes(), 0,
                               accessNumbers2.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }


    public void setSwitchBoardNumber() {
        try {
            recStore.setRecord(5, editSwitchBoardNumber.getString().getBytes(),
                               0,
                               editSwitchBoardNumber.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setSwitchBoardNumber2() {
        try {
            recStore.setRecord(5, editSwitchBoardNumber2.getString().getBytes(),
                               0,
                               editSwitchBoardNumber2.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }


    public void setExtensionNumber() {
        try {
            recStore.setRecord(9, editExtensionNumber.getString().getBytes(),
                               0,
                               editExtensionNumber.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setVoiceExtensionNumber() {
        try {
            recStore.setRecord(9,
                               voiceExtensionEditTextField.getString().getBytes(),
                               0,
                               voiceExtensionEditTextField.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }


    public void setPinCodeNumber() {
        try {
            recStore.setRecord(10, editPinCodeNumber.getString().getBytes(),
                               0,
                               editPinCodeNumber.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setLanguageCallNumbers() {
        try {
            recStore.setRecord(15, countryField.getString().getBytes(),
                               0,
                               countryField.getString().length());
        } catch (Exception e) {
            // ALERT
        }
    }


    public void setPbxAutoSettings(String pbxAutoString) {

        String setNewPbxAutoString = pbxAutoString;

        try {
            recStore.setRecord(11, setNewPbxAutoString.getBytes(),
                               0,
                               setNewPbxAutoString.length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setLanguage(String languagestring) {

        String setNewLanguageString = languagestring;

        try {
            recStore.setRecord(19, setNewLanguageString.getBytes(),
                               0,
                               setNewLanguageString.length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setLanguageCallNumber(String languagestring) {

        String setNewLanguageCallNumberString = languagestring;

        try {
            recStore.setRecord(15, setNewLanguageCallNumberString.getBytes(),
                               0,
                               setNewLanguageCallNumberString.length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setPersonalAttribute(String setPersonalAttribute) {

        String setNewPersonalAttribute = setPersonalAttribute;

        try {
            recStore.setRecord(17, setNewPersonalAttribute.getBytes(), 0,
                               setNewPersonalAttribute.length());

        } catch (Exception e) {
            // ALERT
        }

    }

    public void setSystemAttributeONE(String setSystemAttributeONE) {

        String setNewSystemAttributeONE = setSystemAttributeONE;

        try {
            recStore.setRecord(15, setNewSystemAttributeONE.getBytes(), 0,
                               setNewSystemAttributeONE.length());

        } catch (Exception e) {
            // ALERT
        }

    }


    public void setSystemAttributeTWO(String setSystemAttributeTWO) {

        String setNewSystemAttributeTWO = setSystemAttributeTWO;

        try {
            recStore.setRecord(16, setNewSystemAttributeTWO.getBytes(), 0,
                               setNewSystemAttributeTWO.length());

        } catch (Exception e) {
            // ALERT
        }

    }


    public void setPBXPincodeSettings(String pbxPincodeString) {

        String setNewPBXPincodeString = pbxPincodeString;

        try {
            recStore.setRecord(11, setNewPBXPincodeString.getBytes(),
                               0,
                               setNewPBXPincodeString.length());
        } catch (Exception e) {
            // ALERT
        }
    }

    public void setVoiceEditCode() {

        try {
            recStore.setRecord(18,
                               voiceEditTextField.getString().getBytes(),
                               0,
                               voiceEditTextField.getString().length());
        } catch (Exception e) {
            // ALERT
        }

    }


    public void setPreEditCode() {

        try {
            recStore.setRecord(18,
                               preEditTextField.getString().getBytes(),
                               0,
                               preEditTextField.getString().length());
        } catch (Exception e) {
            // ALERT
        }

    }


    public void setPersonalText_Attribute() {

        try {
            recStore.setRecord(17,
                               editTextPersonalTextField.getString().getBytes(),
                               0,
                               editTextPersonalTextField.getString().length());
        } catch (Exception e) {
            // ALERT
        }

    }

    public void setSystemText_2_Attribute() {

        try {
            recStore.setRecord(16,
                               editTextSystemTextField_2.getString().getBytes(),
                               0,
                               editTextSystemTextField_2.getString().length());
        } catch (Exception e) {
            // ALERT
        }

    }


    public void setSystemText_1_Attribute() {

        try {
            recStore.setRecord(15,
                               editTextSystemTextField_1.getString().getBytes(),
                               0,
                               editTextSystemTextField_1.getString().length());
        } catch (Exception e) {
            // ALERT
        }

    }


    public void setKeyCode() {

        try {
            recStore.setRecord(17, keyCodeTextField.getString().getBytes(),
                               0,
                               keyCodeTextField.getString().length());
        } catch (Exception e) {
            // ALERT
        }

    }

    public void setTWO() { // skiver in i första lediga plats i databasen.. tex. om db 1 - 9 är upptagna skriver metoden in på plats 10...
        try {

            openRecStore();
            String appt = "2";
            byte bytes[] = appt.getBytes();
            recStore.addRecord(bytes, 0, bytes.length);

            closeRecStore();
            upDateDataStore();
            startApp();

        } catch (Exception e) {
            // ALERT
        }
    }


    // ---- GET-metoder ---------

    public String getYear() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte a[] = recStore.getRecord(1);
        setYear = new String(a, 0, a.length);

        closeRecStore();

        return setYear;

    }

    public String getMounth() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte b[] = recStore.getRecord(2);
        setMounth = new String(b, 0, b.length);

        closeRecStore();

        return setMounth;

    }

    public String getDate() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte c[] = recStore.getRecord(3);
        setDate = new String(c, 0, c.length);

        closeRecStore();

        return setDate;

    }

    public String getAccessNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte d[] = recStore.getRecord(4);
        accessNumber_PBX = new String(d, 0, d.length);

        closeRecStore();

        return accessNumber_PBX;

    }


    public String getSwitchBoardNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte e[] = recStore.getRecord(5);
        switchBoardNumber_PBX = new String(e, 0, e.length);

        closeRecStore();

        return switchBoardNumber_PBX;

    }

    public String getThisYearBack() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte f[] = recStore.getRecord(6);
        setyearBack = new String(f, 0, f.length);

        closeRecStore();

        return setyearBack;

    }

    public String getThisMounthBack() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte g[] = recStore.getRecord(7);
        setmounthBack = new String(g, 0, g.length);

        closeRecStore();

        return setmounthBack;

    }

    public String getThisDayBack() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte h[] = recStore.getRecord(8);
        setdayBack = new String(h, 0, h.length);

        closeRecStore();

        return setdayBack;

    }

    public void getTWO() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();
        readRecords();
        readRecordsUpdate();

        try {
            byte i[] = recStore.getRecord(28);
            getTWO = new String(i, 0, i.length);
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreNotOpenException ex) {
        } catch (RecordStoreException ex) {
        }

        try {
            this.dateString = getTWO;
        } catch (Exception ex1) {
        }

        System.out.println("häääääääääärrrrrrrr >>> getTWO >> " + getTWO);
        closeRecStore();

    }

    public String getExtensionNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte j[] = recStore.getRecord(9);
        extensionNumber_PBX = new String(j, 0, j.length);

        closeRecStore();

        return extensionNumber_PBX;

    }

    public String getPinCodeNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte k[] = recStore.getRecord(10);
        pinCodeNumber_PBX = new String(k, 0, k.length);

        closeRecStore();

        return pinCodeNumber_PBX;

    }

    public String getSwitchBoardType() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        String switchboards = "";

        openRecStore();

        byte l[] = recStore.getRecord(11);
        switchboards = new String(l, 0, l.length);

        closeRecStore();

        return switchboards;

    }

    public String getPBXStatus() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(21);
        checkStatus_PBX = new String(l, 0, l.length);

        closeRecStore();

        return checkStatus_PBX;

    }


    public String getKeyCode() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        String url = "";

        openRecStore();

        byte a1[] = recStore.getRecord(17);
        url = new String(a1, 0, a1.length);

        closeRecStore();

        return url;

    }

    public String getDemoStatus() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte l[] = recStore.getRecord(20);
        demoLicens = new String(l, 0, l.length);

        closeRecStore();

        return demoLicens;

    }


    public String getLanguage() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte nlanguage[] = recStore.getRecord(19);
        lang_PBX = new String(nlanguage, 0, nlanguage.length);

        closeRecStore();

        return lang_PBX;
    }

    public String getVoiceEditCode() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        String voiceMailSwitchboard_PBX = "";

        openRecStore();

        byte mTextVoiceEditCode[] = recStore.getRecord(18);
        voiceMailSwitchboard_PBX = new String(mTextVoiceEditCode, 0,
                                              mTextVoiceEditCode.length);

        closeRecStore();

        return voiceMailSwitchboard_PBX;
    }


    public String getPreEditCode() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        String precode_PBX = "";

        openRecStore();

        byte mTextPreEditCode[] = recStore.getRecord(27);
        precode_PBX = new String(mTextPreEditCode, 0,
                                 mTextPreEditCode.length);

        closeRecStore();

        return precode_PBX;
    }


    public String getPersonalText_Attribute() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        String personalText_Attribute = "";

        openRecStore();

        byte mTextPersonal[] = recStore.getRecord(24);
        personalText_Attribute = new String(mTextPersonal, 0,
                                            mTextPersonal.length);

        closeRecStore();

        return personalText_Attribute;
    }


    public String getSystemText_2_Attribute() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        String systemText_2_Attribute = "";

        openRecStore();

        byte mTextSystem_2[] = recStore.getRecord(25);
        systemText_2_Attribute = new String(mTextSystem_2, 0,
                                            mTextSystem_2.length);

        closeRecStore();

        return systemText_2_Attribute;
    }


    public String getSystemText_1_Attribute() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        String getSystemText_1_Attribute = "";

        openRecStore();

        byte mTextSystem_1[] = recStore.getRecord(15);
        getSystemText_1_Attribute = new String(mTextSystem_1, 0,
                                               mTextSystem_1.length);

        closeRecStore();

        return getSystemText_1_Attribute;
    }

    public String getLanguageCallNumber() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();

        byte mlanguage[] = recStore.getRecord(15);
        countryCode_PBX = new String(mlanguage, 0, mlanguage.length);

        closeRecStore();

        return countryCode_PBX;
    }

    public void readRecordsUpdate() {
        try {
            System.out.println("Number of records: " + recStore.getNumRecords());

            if (recStore.getNumRecords() > 0) {
                RecordEnumeration re = recStore.enumerateRecords(null, null, false);
                while (re.hasNextElement()) {
                    String str = new String(re.nextRecord());
                    System.out.println("Record: " + str);
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void readRecords() {
        try {
            // Intentionally small to test code below
            byte[] recData = new byte[5];
            int len;

            for (int i = 1; i <= recStore.getNumRecords(); i++) {
                // Allocate more storage if necessary
                if (recStore.getRecordSize(i) > recData.length) {
                    recData = new byte[recStore.getRecordSize(i)];
                }

                len = recStore.getRecord(i, recData, 0);
                if (Settings.debug) {
                    System.out.println("Record ID#" + i + ": " +
                                       new String(recData, 0, len));
                }
            }
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void writeRecord(String str) {
        byte[] rec = str.getBytes();

        try {
            System.out.println("sparar ");
            recStore.addRecord(rec, 0, rec.length);
            System.out.println("Writing record: " + str);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }


    public void openRecStore() {
        try {
            System.out.println("Öppnar databasen");
            // The second parameter indicates that the record store
            // should be created if it does not exist
            recStore = RecordStore.openRecordStore(REC_STORE, true);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void closeRecStore() {
        try {
            recStore.closeRecordStore();
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void setDataStore() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreNotOpenException,
            RecordStoreException {

        openRecStore();
        readRecords();
        readRecordsUpdate();

        if (recStore.getNumRecords() == 0) { // om innehållet i databasen är '0' så spara de tre första elementen i databasen.

            writeRecord(setYear); // Plats 1.
            writeRecord(setMounth); // Plats 2.
            writeRecord(setDate); // Plats 3.
            writeRecord("9"); // Accessnummer plats 4
            writeRecord("+44"); // Växelnummer  plats 5
            writeRecord(setyearBack); // Plats 6.
            writeRecord(setmounthBack); // Plats 7.
            writeRecord(setdayBack); // Plats 8.
            writeRecord("0"); // Anknytningsnummer plats 9
            writeRecord("0"); // Pinkod    plats 10
            writeRecord("0"); // Pekar på om det är en PBX med autoinlog (3) eller en PBX med PIN-kod login (4)plats 11.
            writeRecord("0"); // Röstbrevlåda Operatör plats 12 // ... 19 FIXAT
            writeRecord("0"); // Debug plats 13
            writeRecord("0"); // Plats 14 LEDIG FÖR NY POST.
            writeRecord("44"); // Landsnummer plats 15 // ... 14 FIXAT
            writeRecord("0"); // mexONOFF plats 16
            writeRecord("0"); // keycode plats 17 // ... 12 FIXAT
            writeRecord("0"); // Röstbrevlåda PBX plats 18 // ... 21 FIXAT
            writeRecord("2"); // Default, Språk engelska plats 19 // ... 13 FIXAT
            writeRecord("0"); // Demo Licens plats 20 FIXAT
            writeRecord("0"); // Check_PBX_Status kontrollerar om config-settings är satt. plats 21
            writeRecord("0"); // Företagsnamn. plats 22
            writeRecord("0"); // Användarnamn plats 23
            writeRecord("System attribute 1"); // system1 text plats 24 // ... 15  KLART
            writeRecord("System attribute 2"); // system1 text plats 25 // ... 16  KLART
            writeRecord("Personal attribute"); // system2 text plats 26 // ... 17  KLART
            writeRecord("*7"); // PreEditCode 'default' text plats 27 // ... 18 KLART

            // SET TWO plats 28

        }

        // sätter nummer i fönstret under inställningar...

        byte d[] = recStore.getRecord(4);
        accessNumber_PBX = new String(d, 0, d.length);

        byte e[] = recStore.getRecord(5);
        switchBoardNumber_PBX = new String(e, 0, e.length);

        byte j[] = recStore.getRecord(9);
        extensionNumber_PBX = new String(j, 0, j.length);

        byte k[] = recStore.getRecord(10);
        pinCodeNumber_PBX = new String(k, 0, k.length);

        byte l[] = recStore.getRecord(11);
        pbx_auto = new String(l, 0, l.length);

        byte m[] = recStore.getRecord(17);
        HGP_PBX = new String(m, 0, m.length);

        byte nlanguage[] = recStore.getRecord(19);
        lang_PBX = new String(nlanguage, 0, nlanguage.length);

        byte mlanguage[] = recStore.getRecord(15);
        countryCode_PBX = new String(mlanguage, 0, mlanguage.length);

        byte mTextSystem_1[] = recStore.getRecord(24);
        systemText_1_Attribute = new String(mTextSystem_1, 0,
                                            mTextSystem_1.length);

        byte mTextSystem_2[] = recStore.getRecord(25);
        systemText_2_Attribute = new String(mTextSystem_2, 0,
                                            mTextSystem_2.length);

        byte mTextPersonal[] = recStore.getRecord(26);
        personalText_Attribute = new String(mTextPersonal, 0,
                                            mTextPersonal.length);

        byte mTextPreEditCode[] = recStore.getRecord(27);
        precode_PBX = new String(mTextPreEditCode, 0,
                                 mTextPreEditCode.length);

        byte mTextVoiceEditCode[] = recStore.getRecord(18); // PBX
        voiceMailSwitchboard_PBX = new String(mTextVoiceEditCode, 0,
                                              mTextVoiceEditCode.length);

        byte mvoiceMessage[] = recStore.getRecord(12); // operatör
        voiceMailOperator_PBX = new String(mvoiceMessage, 0,
                                           mvoiceMessage.length);

        closeRecStore();
    }

    // Om något inputfönster(post) i databasen är tom sätt tillbaka värdet...
    // Det finns totalt 15 olika 'sätt' som databasen kan ha tomma poster med 4 värden.
    // Stämmer med den diskreta matematiken enligt KTH ;-)
    public void upDateDataStore() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        openRecStore();
        String setBackAccessNumberRecord = accessNumber_PBX; // PLATS 4 KLART
        String setBackSwitchBoardNumberRecord = switchBoardNumber_PBX; // PLATS 5 KLART
        String setBackExtensionNumberRecord = extensionNumber_PBX; // PLATS 9 KLART
        String setBackPinCodeNumberRecord = pinCodeNumber_PBX; // PLATS 10 KLART
        String setBackKeyCodeRecord = HGP_PBX; // PLATS 17 KLART
        String setBackLanguageRecord = lang_PBX; // PLATS 15 KLART
        String setBackLanguageCallNumberRecord = countryCode_PBX; // PLATS 15 KLART
        String setBacksystemText_1_Attribute = systemText_1_Attribute; // PLATS 24 KLART
        String setBacksystemText_2_Attribute = systemText_2_Attribute; // PLATS 25 KLART
        String setBackpersonalText_Attribute = personalText_Attribute; // PLATS 26 KLART
        String setBackpreEditCode = precode_PBX; // PLATS 27 KLART
        String setBackvoiceEditCode = voiceMailSwitchboard_PBX; // PLATS 18 KLART , PBX
        String setBackvoiceMessage = voiceMailOperator_PBX; // PLATS 12 KLART , operatör

        if (recStore.getRecord(4) == null && recStore.getRecord(5) == null && // FAll 1 alla 4 poster är tomma. spar in alla tomma värden igen.
            recStore.getRecord(9) == null && recStore.getRecord(10) == null) {

            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(5) == null &&
                   recStore.getRecord(9) == null) {

            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(9) == null &&
                   recStore.getRecord(10) == null) {

            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(5) == null &&
                   recStore.getRecord(10) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(5) == null && recStore.getRecord(9) == null &&
                   recStore.getRecord(10) == null) {
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(5) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(9) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());

        } else if (recStore.getRecord(4) == null && recStore.getRecord(10) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(5) == null && recStore.getRecord(10) == null) {
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(5) == null && recStore.getRecord(9) == null) {
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());

        } else if (recStore.getRecord(9) == null && recStore.getRecord(10) == null) {
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(9) == null && recStore.getRecord(12) == null) {
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(18, setBackvoiceEditCode.getBytes(),
                               0, setBackvoiceEditCode.length());

        } else if (recStore.getRecord(4) == null) {
            recStore.setRecord(4, setBackAccessNumberRecord.getBytes(), 0,
                               setBackAccessNumberRecord.length());

        } else if (recStore.getRecord(5) == null) {
            recStore.setRecord(5, setBackSwitchBoardNumberRecord.getBytes(), 0,
                               setBackSwitchBoardNumberRecord.length());

        } else if (recStore.getRecord(9) == null) {
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());

        } else if (recStore.getRecord(10) == null) {
            recStore.setRecord(10, setBackPinCodeNumberRecord.getBytes(), 0,
                               setBackPinCodeNumberRecord.length());

        } else if (recStore.getRecord(17) == null) {
            recStore.setRecord(17, setBackKeyCodeRecord.getBytes(), 0,
                               setBackKeyCodeRecord.length());

        } else if (recStore.getRecord(19) == null) {
            recStore.setRecord(19, setBackLanguageRecord.getBytes(), 0,
                               setBackLanguageRecord.length());

        } else if (recStore.getRecord(15) == null) {
            recStore.setRecord(15, setBackLanguageCallNumberRecord.getBytes(),
                               0,
                               setBackLanguageCallNumberRecord.length());

        } else if (recStore.getRecord(24) == null) {
            recStore.setRecord(24, setBacksystemText_1_Attribute.getBytes(),
                               0,
                               setBacksystemText_1_Attribute.length());

        } else if (recStore.getRecord(25) == null) {
            recStore.setRecord(25, setBacksystemText_2_Attribute.getBytes(),
                               0,
                               setBacksystemText_2_Attribute.length());

        } else if (recStore.getRecord(26) == null) {
            recStore.setRecord(26, setBackpersonalText_Attribute.getBytes(),
                               0,
                               setBackpersonalText_Attribute.length());

        } else if (recStore.getRecord(27) == null) {
            recStore.setRecord(27, setBackpreEditCode.getBytes(),
                               0,
                               setBackpreEditCode.length());

        } else if (recStore.getRecord(18) == null) {
            recStore.setRecord(18, setBackvoiceEditCode.getBytes(),
                               0,
                               setBackvoiceEditCode.length());

        } else if (recStore.getRecord(9) == null && recStore.getRecord(12) == null) {
            recStore.setRecord(9, setBackExtensionNumberRecord.getBytes(), 0,
                               setBackExtensionNumberRecord.length());
            recStore.setRecord(18, setBackvoiceEditCode.getBytes(),
                               0, setBackvoiceEditCode.length());

        }

        else if (recStore.getRecord(12) == null) {
            recStore.setRecord(12, setBackvoiceMessage.getBytes(),
                               0,
                               setBackvoiceMessage.length());

        }

        closeRecStore();
    }

// ------------------- D A T U M -----------------------------------------------

    public void controllString() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        String readRecord;

        getTWO(); // ställ om i databasen så att kontrollen står mot rätt databaspost.

        readRecord = dateString;

        String viewRecord = readRecord;

        try {
            if (viewRecord.equals("2")) {

                notifyDestroyed();
            }
        } catch (Exception ex) {
        }
        System.out.println("VÄRDET PLATS DB >> " + viewRecord);
    }

    public void controllDate() throws IOException, RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        try {
            this.DBdate = getDate();
        } catch (RecordStoreNotOpenException ex) {
        } catch (InvalidRecordIDException ex) {
        } catch (RecordStoreException ex) {
        }
        try {
            this.DBmounth = getMounth();
        } catch (RecordStoreNotOpenException ex1) {
        } catch (InvalidRecordIDException ex1) {
        } catch (RecordStoreException ex1) {
        }
        try {
            this.DByear = getYear();
        } catch (RecordStoreNotOpenException ex2) {
        } catch (InvalidRecordIDException ex2) {
        } catch (RecordStoreException ex2) {
        }
        try {
            this.DBdateBack = getThisDayBack();
        } catch (RecordStoreNotOpenException ex3) {
        } catch (InvalidRecordIDException ex3) {
        } catch (RecordStoreException ex3) {
        }
        try {
            this.DBmounthBack = getThisMounthBack();
        } catch (RecordStoreNotOpenException ex4) {
        } catch (InvalidRecordIDException ex4) {
        } catch (RecordStoreException ex4) {
        }
        try {
            this.DByearBack = getThisYearBack();
        } catch (RecordStoreNotOpenException ex5) {
        } catch (InvalidRecordIDException ex5) {
        } catch (RecordStoreException ex5) {
        }

        String useDBdate = DBdate.trim();
        String useDBmounth = DBmounth.trim();
        String useDByear = DByear.trim();

        String useDBdateBack = DBdateBack.trim();
        String useDBmounthBack = DBmounthBack.trim();
        String useDByearBack = DByearBack.trim();

        System.out.println("Skriver ut datum om 30 dagar >>> " + useDBdate);
        System.out.println("Skriver ut månad om 30 dagar >>> " + useDBmounth);
        System.out.println("Skriver ut året om 30 dagar >>> " + useDByear);

        System.out.println("Skriver ut Kontroll datum >>> " + useDBdateBack);
        System.out.println("Skriver ut Kontroll månad >>> " + useDBmounthBack);
        System.out.println("Skriver ut Kontroll år >>> " + useDByearBack);

        String toDayDate = checkDay().trim();
        String toDayMounth = checkMounth().trim();

        System.out.println("Skriver ut DAGENS DATUM >>> " + toDayDate);
        System.out.println("Skriver ut ÅRETS MÅNAD >>> " + toDayMounth);

        Integer controllDBdateBack = new Integer(0); // Gör om strängar till integer
        Integer controllDBmonthBack = new Integer(0); // Gör om strängar till integer
        Integer controllDByearBack = new Integer(0); // Gör om strängar till integer

        int INTDBdateBack = controllDBdateBack.parseInt(useDBdateBack);
        int INTDBmounthBack = controllDBmonthBack.parseInt(DBmounthBack);
        int INTDByearBack = controllDByearBack.parseInt(DByearBack);

        Integer controllDBdate = new Integer(0); // Gör om strängar till integer
        Integer controllDBmonth = new Integer(0); // Gör om strängar till integer
        Integer controllDByear = new Integer(0); // Gör om strängar till integer

        Integer controllToDayDBdate = new Integer(0); // Gör om strängar till integer
        Integer controllToDayDBmounth = new Integer(0); // Gör om strängar till integer

        int INTDBdate = controllDBdate.parseInt(useDBdate);
        int INTDBmounth = controllDBmonth.parseInt(DBmounth);
        int INTDByear = controllDByear.parseInt(DByear);

        int INTdateToDay = controllToDayDBdate.parseInt(toDayDate);
        int INTmounthToDay = controllToDayDBmounth.parseInt(toDayMounth);

        if (INTDBdate <= INTdateToDay && INTDBmounth <= INTmounthToDay &&
            INTDByear == checkYear) {

            System.out.println("SANN SANN SANN SANN SANN ");

            setTWO(); // Om månad och datum är sann skriv in "2" i databasen plats 10...

        }
        if (INTmounthToDay == 0) { // Om INTmounthToDay har värdet '0' som är januari

            INTDBmounthBack = 0; // Då innehåller installations-månaden samma värde som nu-månaden.

        }
        if (INTDBmounthBack > INTmounthToDay) { // Om installations-månaden är större än 'dagens' månad som är satt i mobilen så stäng...

            setTWO(); // Om månad och datum är sann skriv in "2" i databasen plats 10...

        }
        if (INTDBmounthBack > INTmounthToDay && INTDByearBack < checkYear) { // Om installations-månaden är större än 'dagens' månad som är satt i mobilen så stäng...

            setTWO(); // Om månad och datum är sann skriv in "2" i databasen plats 10...

        }
        if (INTDByearBack > checkYear) { // Om installations-året är större än året som är satt i mobilen. >> går bakåt i tiden...

            setTWO(); // Om månad och datum är sann skriv in "2" i databasen plats 10...

        }
        if (INTDBdateBack > INTdateToDay && INTDBmounthBack > INTmounthToDay &&
            INTDByearBack > checkYear) {

            setTWO(); // Om månad och datum är sann skriv in "2" i databasen plats 10...

        }
        if (INTDBmounthBack > INTmounthToDay && INTDByearBack > checkYear) {

            setTWO(); // Om månad och datum är sann skriv in "2" i databasen plats 10...

        }

    }


    public void setDBDate() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        countDay();

        System.out.println("Om 30 dagar är det den >> " + dayAfter +
                           ", och månad >> " + monthAfter + " det är år >> " +
                           yearAfter);

        String convertDayAfter = Integer.toString(dayAfter); // konvertera int till string...
        String convertMounthAfter = Integer.toString(monthAfter);
        String convertYearAfter = Integer.toString(yearAfter);

        this.setDate = convertDayAfter;
        this.setMounth = convertMounthAfter;
        this.setYear = convertYearAfter;

    }

    public void setDBDateBack() {

        countThisDay();

        System.out.println("Kontrollerar dagens dautm >> " + dayBack +
                           ", och månad >> " + mounthBack + " det är år >> " +
                           yearBack);

        String convertDayBack = Integer.toString(dayBack); // konvertera int till string...
        String convertMounthBack = Integer.toString(mounthBack);
        String convertYearBack = Integer.toString(yearBack);

        this.setdayBack = convertDayBack;
        this.setmounthBack = convertMounthBack;
        this.setyearBack = convertYearBack;

    }

    public void countThisDay() {

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        System.out.println("Dagens datum är den >> " + day +
                           ", Årets månad är nummer >> " + month +
                           " det är år >> " + year);

        this.dayBack = day;
        this.mounthBack = month;
        this.yearBack = year;

    }

    public void countDay() {

        // Get today's day and month
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        System.out.println("Dagens datum är den >> " + day +
                           ", Årets månad är nummer >> " + month +
                           " det är år >> " + year);
        this.checkYear = year;

        // Räknar fram 30 dagar framåt vilket datum år osv...
        final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;
        long offset = date.getTime();
        offset += antalDagar * MILLIS_PER_DAY;
        date.setTime(offset);
        cal.setTime(date);

        // Now get the adjusted date back
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        year = cal.get(Calendar.YEAR);

        this.dayAfter = day;
        this.monthAfter = month;
        this.yearAfter = year;

    }

    private String regFromTextFile() { // Läser textfilen tmp.txt
        InputStream is = null;
        try {
            is = getClass().getResourceAsStream("file:///root1://dbg.txt");
        } catch (Exception ex) {
        }
        try {
            StringBuffer sb = new StringBuffer();
            int chr, i = 0;
            // Read until the end of the stream
            while ((chr = is.read()) != -1) {
                sb.append((char) chr);
            }

            getResponseTextBox().setString(sb.toString());

            return sb.toString();

        } catch (Exception e) {
            System.out.println("Unable to create stream");
            System.out.println(e.getMessage());
        }
        return null;
    }

    /* private void fileReadandWrite() {
         try {
             FileConnection fcon = (FileConnection) Connector.open(
                     "file:///c:/pictures/sample.txt", Connector.READ_WRITE);
             if (fcon.isOpen()) {
                 if (fcon.exists())
                     fcon.delete();
                 fcon.create();
                 DataOutputStream dout = new DataOutputStream(fcon.
                         openOutputStream());
                 dout.writeUTF("Sample Testing");
                 dout.close();
                 fcon.close();
                 fcon = null;
             }
             if (fcon == null) {
                 fcon = (FileConnection) Connector.open(
     "file:///c:/pictures/sample.txt", Connector.READ_WRITE);
                 if (fcon.isOpen()) {
                     if (fcon.exists()) {
                         DataInputStream din = new DataInputStream(fcon.
                                 openDataInputStream());
                         f.append(din.readUTF());
                         din.close();
                         fcon.close();
                         fcon = null;
                     }
                 }
             }
         } catch (Exception e) {
             f.append("error " + e.getMessage());
         }
     }*/


    public String setViewDateString() throws InvalidRecordIDException,
            RecordStoreNotOpenException, RecordStoreException {

        //ViewDateString

        String e1 = getDate();
        String e2 = setMounth();
        String e3 = getYear();

        ViewDateString = e1 + " " + e2 + " " + e3;

        return ViewDateString;

    }

    public String setMounth() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        setViewMounth = getMounth();

        if (setViewMounth.equals("0")) {

            this.setViewMounth = "Januari";
        }
        if (setViewMounth.equals("1")) {

            this.setViewMounth = "Februari";
        }
        if (setViewMounth.equals("2")) {

            this.setViewMounth = "Mars";
        }
        if (setViewMounth.equals("3")) {

            this.setViewMounth = "April";
        }
        if (setViewMounth.equals("4")) {

            this.setViewMounth = "Maj";
        }
        if (setViewMounth.equals("5")) {

            this.setViewMounth = "Juni";
        }
        if (setViewMounth.equals("6")) {

            this.setViewMounth = "Juli";
        }
        if (setViewMounth.equals("7")) {

            this.setViewMounth = "Augusti";
        }
        if (setViewMounth.equals("8")) {

            this.setViewMounth = "September";
        }
        if (setViewMounth.equals("9")) {

            this.setViewMounth = "Oktober";
        }
        if (setViewMounth.equals("10")) {

            this.setViewMounth = "November";
        }
        if (setViewMounth.equals("11")) {

            this.setViewMounth = "December";
        }

        String viewMounth = setViewMounth;

        return viewMounth;
    }

    public String checkDay() {

        String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumvärde, skickar och gör om till en string av java.lang.string-typ

        String checkDayString = mobileClock.substring(8, 10); // plockar ut 'datum' tecken ur klockan

        if (checkDayString.equals("01")) {

            checkDayString = "1";

        } else if (checkDayString.equals("02")) {

            checkDayString = "2";

        } else if (checkDayString.equals("03")) {

            checkDayString = "3";

        } else if (checkDayString.equals("04")) {

            checkDayString = "4";

        } else if (checkDayString.equals("05")) {

            checkDayString = "5";

        } else if (checkDayString.equals("06")) {

            checkDayString = "6";

        } else if (checkDayString.equals("07")) {

            checkDayString = "7";

        } else if (checkDayString.equals("08")) {

            checkDayString = "8";

        } else if (checkDayString.equals("09")) {

            checkDayString = "9";

        }

        String useStringDate = checkDayString;

        return useStringDate;

    }

    public String checkMounth() {

        String mobileClock = today.toString(); // Tilldelar mobileClock 'todays' datumvärde, skickar och gör om till en string av java.lang.string-typ

        String checkMounthString = mobileClock.substring(4, 7); // plockar ut 'Månad' tecken ur klockan

        if (checkMounthString.equals("Jan")) {

            checkMounthString = "0";

        } else if (checkMounthString.equals("Feb")) {

            checkMounthString = "1";

        } else if (checkMounthString.equals("Mar")) {

            checkMounthString = "2";

        } else if (checkMounthString.equals("Apr")) {

            checkMounthString = "3";

        } else if (checkMounthString.equals("May")) {

            checkMounthString = "4";

        } else if (checkMounthString.equals("Jun")) {

            checkMounthString = "5";

        } else if (checkMounthString.equals("Jul")) {

            checkMounthString = "6";

        } else if (checkMounthString.equals("Aug")) {

            checkMounthString = "7";

        } else if (checkMounthString.equals("Sep")) {

            checkMounthString = "8";

        } else if (checkMounthString.equals("Oct")) {

            checkMounthString = "9";

        } else if (checkMounthString.equals("Nov")) {

            checkMounthString = "10";

        } else if (checkMounthString.equals("Dec")) {

            checkMounthString = "11";

        }

        String useStringMounth = checkMounthString;

        return useStringMounth;

    }

    //---------- DELETE INDEX ID, SAVE, UPDATE RMS -----------------------------

    private void displayAlert(int type, String msg, Screen s) {
        alert.setString(msg);

        switch (type) {
        case ERROR:
            alert.setTitle(alertDefaultError_DEF);
            alert.setType(AlertType.ERROR);

            break;
        case INFO:
            alert.setTitle(alertDefaultInfo_DEF);
            alert.setType(AlertType.INFO);
            break;
        case SIZE:
            alert.setTitle(alertDefaultMaxSize_DEF);
            alert.setType(AlertType.ERROR);
            break;

        case INPUT:
            alert.setTitle(alertDefaultError_DEF);
            alert.setType(AlertType.ERROR);
            break;

        case NAMEERROR:
            alert.setTitle(alertDefaultError_DEF);
            alert.setType(AlertType.ERROR);
            break;

        }
        Display.getDisplay(this).setCurrent(alert,
                                            s == null ? display.getCurrent() :
                                            s);

    }

    public static void sortInteger(int[] v) {

        int t = 0;
        int counter = v.length - 1;
        for (int i = 0; i < v.length - 1; i++) {
            for (int j = 0; j < counter; j++) {
                if (v[j] > v[j + 1]) {
                    t = v[j];
                    v[j] = v[j + 1];
                    v[j + 1] = t;
                }
            }

            counter--;
        }
    }

    public void sortRecords() throws RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        try {
            addrBook = RecordStore.openRecordStore("TheAddressBook", true);
        } catch (RecordStoreException e) {
            addrBook = null;
        }

        int lastID = addrBook.getNextRecordID();
        int numRecords = addrBook.getNumRecords();
        int count = 0;

        for (int id = 1;
                      id < lastID && count < numRecords;
                      ++id) {
            try {
                byte[] data = addrBook.getRecord(id);
                // process the data
                ++count;
            } catch (InvalidRecordIDException e) {
                // just ignore and move to the next record
            } catch (RecordStoreException e) {
                // a more general error that should be handled
                // somehow
                break;
            }
        }

    }

    public void deleteDialedNumbersRecord(int delete) throws
            RecordStoreNotOpenException,
            InvalidRecordIDException, RecordStoreException {

        /*String delete = deleteBox.getString();
                     int ID = 0;
                     ID = Integer.parseInt(delete);*/
        int p = 0;
        int recordID = delete;

        System.out.println("recordID >>>>>>>>>>>> " + recordID);

        //refreshShortnumbers();

        try {
            addrBook = RecordStore.openRecordStore("TheAddressBook", true);
        } catch (RecordStoreException e) {
            addrBook = null;
        }

        System.out.println("deleting ID #" + String.valueOf(recordID));
        /*
         * delete one of the string records
         */
        try {
            System.out.println("deleting ID #" + String.valueOf(recordID));
            addrBook.deleteRecord(recordID);
        } catch (RecordStoreException rse) {
            System.err.println(rse.toString());
        }

        //recordID = insertString("baz", recordStore);

        /*
         * Build an enumeration to index through all records. Notice
         * the hole where the one string record was deleted.
         */
        RecordEnumeration re = null;
        try {
            re = addrBook.enumerateRecords((RecordFilter)null,
                                           (RecordComparator)null, false);
        } catch (RecordStoreNotOpenException rsnoe) {
            System.err.println(rsnoe.toString());
        }

        while (re.hasNextElement()) {
            try {
                System.out.println("Next Record ID = " +
                                   re.nextRecordId());
            } catch (InvalidRecordIDException iride) {
                System.err.println(iride.toString());
            }
        }

        /*
         * Clean up the RecordEnumeration
         */


        /*
         * Build a new RecordEnumeration with a filter to include only
         * integer records. Notice how it will only report the integer
         * records.
         */
        IntegerFilter iFilt = new IntegerFilter();
        try {
            re = addrBook.enumerateRecords((RecordFilter) iFilt,
                                           null, false);
        } catch (RecordStoreNotOpenException rsnoe) {
            System.err.println(rsnoe.toString());
        }

        /*
         * And walk through it backwards for kicks...
         */
        while (re.hasPreviousElement()) {
            try {
                System.out.println("Previous Record ID = " +
                                   re.previousRecordId());
            } catch (InvalidRecordIDException iride) {
                System.err.println(iride.toString());
            }
        }

        /*
         * Clean up the RecordEnumeration
         */
        re.destroy();

        //Display.getDisplay(this).setCurrent(genNameScr(menyDefaultCalled, null, null, true));
        try {
            addrBook.closeRecordStore();
            //recordStore.deleteRecordStore(RECORD_STORE_NAME);
        } catch (RecordStoreException rse) {
            System.err.println(rse.toString());
        }

    }

    public void deleteAllRecords() throws RecordStoreException {

        RecordStore rs = null;
        RecordEnumeration re = null;
        try {
            rs = RecordStore.openRecordStore("TheAddressBook", true);
            re = rs.enumerateRecords(null, null, false);

            // First remove all records, a little clumsy.
            while (re.hasNextElement()) {
                int id = re.nextRecordId();
                rs.deleteRecord(id);
            }
        } catch (Exception ex) {
        }

    }


    public String sortCharAt(String s) {

        String sString = s; // sortString innehåller samma som för IMEI-strängen för att kunna kontrollera å sortera bort tecken....

        StringBuffer bTecken = new StringBuffer(sString); // Lägg strängen sortString i ett stringbuffer objekt...

        for (int i = 0; i < bTecken.length(); i++) { // räkna upp hela bTecken-strängens innehåll hela dess längd

            char tecken = bTecken.charAt(i); // char tecken är innehållet i hela längden

            /* Tecknen täcker >> Tyska, Dutch, Svenska, Engelska, Norska, Danska.
                Spanska, Italenska, Finska, Franska */

            //

            if ('A' <= tecken && tecken <= 'Z' ||
                'a' <= tecken && tecken <= 'z'
                || tecken == '-' || tecken == '/' || tecken == '\\' ||
                tecken == ':' || tecken == ';'
                || tecken == '.' || tecken == ',' || tecken == '_' ||
                tecken == '|' || tecken == '<'
                || tecken == '>' || tecken == '+' || tecken == '(' ||
                tecken == ')' || tecken == 'å' || tecken == 'Å' ||
                tecken == 'ä' || tecken == 'Ä' || tecken == 'ö' ||
                tecken == 'Ö' ||
                tecken == 'Ü' || tecken == 'ü' || tecken == 'ß' ||
                tecken == 'Æ' || tecken == 'æ' || tecken == 'Ø' ||
                tecken == 'á' || tecken == 'Á' || tecken == 'é' ||
                tecken == 'É' || tecken == 'í' || tecken == 'Í' ||
                tecken == 'œ' || tecken == 'Œ' || tecken == 'š' ||
                tecken == 'Š' || tecken == 'ž' || tecken == 'Ž' ||
                tecken == 'ó' || tecken == 'Ó' || tecken == 'ú' ||
                tecken == 'Ú' || tecken == 'ñ' || tecken == 'Ñ' ||
                tecken == 'ø') {

                bTecken.setCharAt(i, ' '); // lägg in blanksteg i strängen där något av ovanstående tecken finns....
            }

        }

        bTecken.append(' '); // lägger till blanksteg sist i raden så att sista kommer med för att do-satsen ska kunna hitta och sortera...

        String setString = new String(bTecken); // Gör om char-strängen till en string-sträng

        int antal = 0;
        char separator = ' '; // för att kunna sortera i do-satsen

        int index = 0;

        do { // do satsen sorterar ut blankstegen och gör en ny sträng för att jämföra IMEI med...
            ++antal;
            ++index;

            index = setString.indexOf(separator, index);
        } while (index != -1);

        subStr = new String[antal];
        index = 0;
        int slutindex = 0;

        for (int j = 0; j < antal; j++) {

            slutindex = setString.indexOf(separator, index);

            if (slutindex == -1) {
                subStr[j] = setString.substring(index);
            }

            else {
                subStr[j] = setString.substring(index, slutindex);
            }

            index = slutindex + 1;

        }
        String setNumber = "";
        for (int k = 0; k < subStr.length; k++) {

            setNumber += subStr[k]; // Lägg in värdena från subStr[k] i strängen setNumber....
        }

        System.out.println("Sorterad: " + setNumber);

        String sortedString = setNumber;

        return sortedString;
    }


}

