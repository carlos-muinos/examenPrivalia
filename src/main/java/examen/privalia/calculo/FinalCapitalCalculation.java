package examen.privalia.calculo;

import static examen.privalia.util.GetInvesmentDays.getInvesmentDays;
import static examen.privalia.util.CsvParser.manageFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class FinalCapitalCalculation {

    final static double MONTHLY_INVESMENT = 50;
    final static double BROKER_PERCENTAGE = 0.02;
    final static double BROKER_COMMISSION = MONTHLY_INVESMENT * BROKER_PERCENTAGE;
    final static double MONTHLY_NET_INVESMENT = MONTHLY_INVESMENT - BROKER_COMMISSION;
    final static BigDecimal SALE_DAY_CLOSING_VALUE = new BigDecimal(29.17);

    static final Logger logger = Logger.getLogger(FinalCapitalCalculation.class);

    public static void main(String args[]) throws ParseException, IOException, ClassNotFoundException {
        List<String> invesmentDaysList = (List<String>) getInvesmentDays();
        List<String> parsedFileContent = (List<String>) manageFile();
        List<String> dateUpdatedParsedFileContent = updateFileDateFormat(parsedFileContent);

        BigDecimal totalBuyedActions = getTotalBuyedActions(invesmentDaysList, dateUpdatedParsedFileContent);
        BigDecimal totalCapitalObtained = calculateTotalCapital(totalBuyedActions, SALE_DAY_CLOSING_VALUE);

        System.out.println("El capital total obtenido es de: " + totalCapitalObtained.setScale(3,BigDecimal.ROUND_HALF_UP) + " euros");
        logger.info("El capital total obtenido es de: " + totalCapitalObtained.setScale(3,BigDecimal.ROUND_HALF_UP) + " euros");
    }

    public static List<String> updateFileDateFormat(List<String> parsedFileContent){
        for(String item : parsedFileContent){
            if(item.contains("-")){
                String[] textDate = item.split("-");
                String numericFormatedMonth = setNumericMonth(textDate[1]);
                String formatedDate = textDate[0] + "-" + numericFormatedMonth  + "-" + textDate[2];
                int listIndex = parsedFileContent.indexOf(item);
                parsedFileContent.set(listIndex, formatedDate);
            }
        }
        return parsedFileContent;
    }

    public static String setNumericMonth(String valor){
        switch(valor) {
            case "ene" :
                return "01";
            case "feb" :
                return "02";
            case "mar" :
                return "03";
            case "abr" :
                return "04";
            case "may" :
                return "05";
            case "jun" :
                return "06";
            case "jul" :
                return "07";
            case "ago" :
                return "08";
            case "sep" :
                return "09";
            case "oct" :
                return "10";
            case "nov" :
                return "11";
            case "dic" :
                return "12";
            default:
                return "01";
        }
    }

    public static BigDecimal getTotalBuyedActions(List<String> invesmentDaysList, List<String> FileContent) throws ParseException {
        BigDecimal totalBuyedActionsQuantity = new BigDecimal(0);
        for(String invesmentDay : invesmentDaysList){
            for(String item : FileContent){

                if(item.contains(invesmentDay)){
                    int fileContentIndex = FileContent.indexOf(item);
                    int openingValueIndex = fileContentIndex - 2;
                    BigDecimal monthlyBuyedActionsQuantity = saveDailyRecord(invesmentDay, openingValueIndex, FileContent);
                    totalBuyedActionsQuantity = totalBuyedActionsQuantity.add(monthlyBuyedActionsQuantity);
                    break;
                }else{
                    if(item.contains("-")){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        Date dateInvesmentDay = sdf.parse(invesmentDay);
                        Date dateFile = sdf.parse(item);
                        if(dateInvesmentDay.before(dateFile)){
                            int fileContentIndex = FileContent.indexOf(item);
                            int openingValueIndex = fileContentIndex - 2;
                            BigDecimal monthlyBuyedActionsQuantity = saveDailyRecord(invesmentDay, openingValueIndex, FileContent);
                            totalBuyedActionsQuantity.add(monthlyBuyedActionsQuantity);
                            totalBuyedActionsQuantity = totalBuyedActionsQuantity.add(monthlyBuyedActionsQuantity);
                            break;
                        }
                    }
                }

            }
        }
        return (totalBuyedActionsQuantity);
    }

    public static BigDecimal saveDailyRecord(String invesmentDay, int openingValueIndex, List<String> FileContent){
        double openingValue = Double.parseDouble(FileContent.get(openingValueIndex));
        double buyedActions = MONTHLY_NET_INVESMENT / openingValue;
        BigDecimal monthlyBuyedActionsQuantity = new BigDecimal(buyedActions).setScale(3,BigDecimal.ROUND_HALF_UP);

        List<String> buyedActionsHistoricalRecord = new ArrayList<>();
        buyedActionsHistoricalRecord.add("El día " + invesmentDay
                + " el valor de compra de la acción era de " +  openingValue
                + " con lo cual ese mes ha comporado " + monthlyBuyedActionsQuantity + " acciones");

        for (String item: buyedActionsHistoricalRecord) {
            logger.info(item);
        }

        return monthlyBuyedActionsQuantity;
    }

    public static BigDecimal calculateTotalCapital(BigDecimal totalBuyedActions, BigDecimal saleDayClosingValue){
        BigDecimal totalCapitalObtained = new BigDecimal(0);
        totalCapitalObtained = totalBuyedActions.multiply(saleDayClosingValue);
        return (totalCapitalObtained);
    }
}
