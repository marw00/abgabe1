public class KidneyCalc {

        public enum Gender {
            MALE, FEMALE
        }

        public enum SkinColor {
            LIGHT, DARK
        }

        private static final double STANDARD_BSA = 1.73;

        private static double calculateBSA(double weight, double height) {
            return Math.sqrt((height * weight) / 3600);
        }

        public static double calculateEGFR(int age, double creatinine, double weight, double height, Gender gender, SkinColor skinColor) {
            double eGFR;
            double k;
            double alpha;
            double correctionFactor = 1.0;

            if (gender == Gender.FEMALE) {
                k = 0.7;
                alpha = -0.329;
                correctionFactor *= 1.018;
            } else {
                k = 0.9;
                alpha = -0.411;
            }

            if (skinColor == SkinColor.DARK) {
                correctionFactor *= 1.159;
            }

            double sCreaDivK = creatinine / k;
            double minVal = Math.min(sCreaDivK, 1);
            double maxVal = Math.max(sCreaDivK, 1);

            eGFR = 141 * Math.pow(minVal, alpha) * Math.pow(maxVal, -1.209) * Math.pow(0.993, age) * correctionFactor;

            double bsa = calculateBSA(weight, height);
            eGFR = eGFR * (bsa / STANDARD_BSA);

            return eGFR;
        }

        public static void main(String[] args) {
            int age = 30;
            double creatinine = 1;
            double weight = 90;
            double height = 180;
            SkinColor skinColor = SkinColor.LIGHT;
            Gender gender = Gender.MALE;

            double egfr = calculateEGFR(age, creatinine, weight, height, gender, skinColor);
            System.out.printf("The estimated eGFR is: %.2f mL/min/1.73m²%n", egfr);
        }
    }
