public class BankCalculation
{
  public static void main(String[] args)
  {
    final float monthlySaving = FixedDeposit.getMonthlySaving(15/4, 2175000, 36, 3);
    System.out.println(monthlySaving);
    //final float monthlyPayment = LoanCalculator.getMonthlyPayment(12, 36, 1500000);
    //System.out.println(monthlyPayment);
    final float finalAmount = FixedDeposit.getFinalAmount(15 / 4, 56666, 36, 3);
    System.out.println(finalAmount);
    final double numberOfMonths = FixedDeposit.getNumberOfMonths(15 / 4, 1500000, 50000, 3);
    System.out.println(numberOfMonths);
  }
}

class LoanCalculator
{
  static float getMonthlyPayment(float annualPercent, int numberOfMonths, float loanAmount)
  {
    float rate = annualPercent / 100f;
    float perMonthCapitalReturn = loanAmount / numberOfMonths;
    float interestPerMonth = loanAmount * rate / 12;
    return perMonthCapitalReturn + interestPerMonth;
  }

  static float getTotalInterestPayable(float annualPercent, int numberOfMonths, float loanAmount)
  {
    float rate = annualPercent / 100f;
    float interestPerMonth = loanAmount * rate / 12;
    return interestPerMonth * numberOfMonths;
  }

  static float getTotalAmountPayable(float annualPercent, int numberOfMonths, float loanAmount)
  {
    return getMonthlyPayment(annualPercent, numberOfMonths, loanAmount) * numberOfMonths;
  }
}

/**
 * saving for each term = y
 * to get final amount in a term (1+rate) * termSaving.
 * after 1 term there will be (1 + rate) * termSaving + y
 * like that after n term final amount = termSaving * (1 + (1+rate) + (1+rate)^2 + ... + (1+rate)^n-1)
 * = termSaving * summation of series
 * = termSaving * 1 * ((1+rate)^(term-1) - 1)/rate
 * = termSaving * ((1+rate)^(term-1) - 1)/rate
 */
class FixedDeposit
{
  static float getTotalInterest(int percentForTerm, float monthlySaving, int numberOfMonths, int monthsPerTerm)
  {
    final float finalAmount = getFinalAmount(percentForTerm, monthlySaving, numberOfMonths, monthsPerTerm);
    final float givenAmount = monthlySaving * numberOfMonths;
    return finalAmount - givenAmount;
  }

  static float getFinalAmount(int percentForTerm, float monthlySaving, int numberOfMonths, int monthsPerTerm)
  {
    float rate = percentForTerm / 100f;
    float calculatedRate = 1 + rate;
    int numberOfTerms = numberOfMonths / monthsPerTerm;
    float termSaving = monthlySaving * monthsPerTerm;
    return (float) ((termSaving * (Math.pow(calculatedRate, numberOfTerms - 1) - 1)) / rate);
  }

  static float getMonthlySaving(int percentForTerm, float finalAmount, int numberOfMonths, int monthsPerTerm)
  {
    float rate = percentForTerm / 100f;
    float calculatedRate = 1 + rate;
    int numberOfTerms = numberOfMonths / monthsPerTerm;
    final double termSaving = finalAmount * rate / (Math.pow(calculatedRate, numberOfTerms - 1) - 1);
    return (float) (termSaving / monthsPerTerm);
  }

  static double getNumberOfMonths(int percentForTerm, float finalAmount, float monthlySaving, int monthsPerTerm)
  {
    float rate = percentForTerm / 100f;
    float calculatedRate = 1 + rate;
    double termSaving = monthlySaving * monthsPerTerm;
    double numberOfTerms = (Math.log10((finalAmount * rate / termSaving) + 1) / Math.log10(calculatedRate)) + 1;
    return (numberOfTerms * monthsPerTerm);
  }
}
