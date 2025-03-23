package seedu.duke;

import ui.Ui;
import instrument.InstrumentList;

public class Duke {
    private static final String HELP = "help";
    private static final String LIST = "list";
    private static final String ADD = "add";
    private static final String DELETE = "delete";
    private static final String RESERVE = "reserve";
    private static final String RETURN = "return";
    private static final String EXIT = "exit";


    /**
     * Main entry-point for the java.duke.Duke application.
     */
    private Ui ui;
    private InstrumentList instrumentList;

    public Duke() {
        ui = new Ui();
        instrumentList = new InstrumentList();
    }

    public void runDuke() {

        assert ui != null;
        ui.printStartMessage();

        while (true) {
            try {
                String userInput = ui.readUserInput();
                String command = ui.getCommand(userInput);
                String input = ui.getRemainingWords(userInput);

                assert userInput != null;
                assert command != null;
                assert input != null;
                
                switch (command) {
                case HELP:
                    ui.printCommandList();
                    break;
                case LIST:
                    ui.printInstrumentList(instrumentList.getList());
                    break;
                case ADD:
                    instrumentList.addInstrument(ui.getNameModelYear(input));
                    ui.printInstrumentList(instrumentList.getList());
                    break;
                case DELETE:
                    instrumentList.deleteInstrument(Integer.parseInt(input));
                    ui.printInstrumentList(instrumentList.getList());
                    break;
                case RESERVE:
                    instrumentList.reserveInstrument(Integer.parseInt(input));
                    ui.printInstrumentList(instrumentList.getList());
                    break;
                case RETURN:
                    instrumentList.returnInstrument(Integer.parseInt(input));
                    ui.printInstrumentList(instrumentList.getList());
                    break;
                case EXIT:
                    ui.printGoodbye();
                    return;
                default:
                    ui.printNoMatchingCommandError();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void main(String[] args) {
        new Duke().runDuke();
    }
}
