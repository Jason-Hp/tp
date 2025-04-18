package commands.instrument;

import commands.Command;
import exceptions.instrument.IncorrectDescriptionException;
import exceptions.instrument.IncorrectReserveInstrumentException;
import instrument.InstrumentList;
import parser.CommandParser;
import ui.Ui;
import user.UserUtils;
import finance.FinanceManager;
import utils.DateTimeParser;

import java.time.LocalDate;

public class ReserveCommand extends Command {
    private CommandParser parser;

    public ReserveCommand(String command) {
        super(command);
        parser = new CommandParser();
    }

    /**
     * Reserves item on instrument list
     *
     * @param instrumentList list of instruments
     * @param ui             UI object from SirDuke
     * @param userUtils      userUtils from SirDuke
     * @param financeManager financeManger from SirDuke
     */
    @Override
    public void execute(InstrumentList instrumentList, Ui ui, UserUtils userUtils, FinanceManager financeManager) {
        try {
            String[] userInput = parser.splits(this.name);
            int indice = Integer.parseInt(userInput[0]);
            if (userInput.length > 1) {
                if (reserveFromTo(instrumentList, indice)) {
                    return;
                }
            } else {
                reserveNil(instrumentList, indice);
            }
            ui.printInstrumentList(instrumentList.getList());
        } catch (NumberFormatException e) {
            throw new IncorrectDescriptionException("""
                    Invalid reserve instrument, please follow\s
                    reserve INSTRUMENT_NUMBER\s
                    OR\s
                    reserve INSTRUMENT_NUMBER from: START_DATE to: END_DATE""");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void reserveNil(InstrumentList instrumentList, int indice) {
        try {
            instrumentList.reserveInstrumentFromTo(indice, LocalDate.now(), null);
        } catch (Exception | AssertionError e) {
            throw new IncorrectReserveInstrumentException(e.getMessage());
        }
    }

    private boolean reserveFromTo(InstrumentList instrumentList, int indice) {
        try {
            String[] parts = this.name.split("from: |to: ", 3);
            if (parts.length < 2) {
                throw new IncorrectReserveInstrumentException("Invalid format: ");
            }
            LocalDate from = DateTimeParser.parseDate(parts[1]);
            LocalDate to = DateTimeParser.parseDate(parts[2]);
            assert from.isBefore(to) : "from: date must be before to: date";
            assert from.isAfter(LocalDate.now().minusDays(1)) :
                    "from: date must be either today or a date in the future";
            if (from.isAfter(to)) {
                throw new IncorrectReserveInstrumentException("from: date must be after to: date");
            } else if (from.isBefore(LocalDate.now().minusDays(1))) {
                throw new IncorrectReserveInstrumentException("from: date must be either today " +
                        "or a date in the future");
            }
            instrumentList.reserveInstrumentFromTo(indice, from, to);
        } catch (Exception | AssertionError e) {
            throw new IncorrectReserveInstrumentException(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
