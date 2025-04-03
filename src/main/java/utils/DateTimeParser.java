package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;

public class DateTimeParser {
    private static final List<DateTimeFormatter> DATE_FORMATS = Arrays.asList(
            DateTimeFormatter.ofPattern("d/M/yyyy"),     // Day/Month/Year with 4-digit year
            DateTimeFormatter.ofPattern("d/M/yy"),        // Day/Month/Year with 2-digit year
            DateTimeFormatter.ofPattern("MMM dd yyyy"),  // Month (abbreviated)/Day/Year
            DateTimeFormatter.ofPattern("dd/MM/yyyy"),   // Day/Month/Year with 4-digit year
            DateTimeFormatter.ofPattern("dd/MM/yy"),     // Day/Month/Year with 2-digit year
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),   // ISO format Year-Month-Day
            DateTimeFormatter.ofPattern("MM-dd-yyyy"),   // Month-Day-Year format
            DateTimeFormatter.ofPattern("dd MMMM yyyy"), // Day FullMonth Year
            DateTimeFormatter.ofPattern("d MMM yyyy")    // Day AbbrMonth Year
    );

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d/M/yyyy");

    public static LocalDate parseDate(String input) throws DateTimeParseException {
        if (input == null || input.equals("null") || input.isEmpty()) {
            return null;
        }
        input = input.trim();
        for (DateTimeFormatter format : DATE_FORMATS) {
            try {
                return LocalDate.parse(input, format);
            } catch (DateTimeParseException e) {
                System.out.println("Failed to parse '" + input + "' with format: " + format);
            }
        }
        throw new DateTimeParseException("Unable to parse date: " + input, input, 0);
    }

    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    public static void main(String[] args) {
        String[] testInputs = {
                "01/01/2023",   // Expected: 2023-01-01
                "Jul 04 2023",   // Expected: 2023-07-04
                "31/12/99",      // Expected: 1999-12-31
                "5 Jun 2023",    // Expected: 2023-06-05
                "2023-07-01",    // Expected: 2023-07-01
                "invalid_date"   // Expected: Error message
        };

        for (String input : testInputs) {
            try {
                LocalDate parsedDate = parseDate(input);
                System.out.println("Input: " + input + " -> Parsed: " + parsedDate);
            } catch (DateTimeParseException e) {
                System.out.println("Final failure: Unable to parse '" + input + "'");
            }
        }
    }
}
