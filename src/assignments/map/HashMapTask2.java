package assignments.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class HashMapTask2 {
    static class Citizen implements Comparable<Citizen>
    {
        final static Map<String, Integer> parseIndices = new LinkedHashMap<String, Integer>()
        {
            private boolean locked;
            {
                put("street", 21);
                put("streetNumber", 37);
                put("stop0", 46);
                put("lastName", 56);
                put("firstName", 72);
                put("relation", 89);
                put("ownOrRent", 109);
                put("rentValue", 114);
                put("stop1", 122);
                put("sex", 134);
                put("stop2", 139);
                put("age", 144);
                put("maritalStatus", 152);
                put("firstMarriage", 157);
                put("attendSchool", 163);
                put("canRead", 168);
                put("birthplace", 174);
                put("birthplaceDad", 191);
                put("birthplaceMom", 208);
                put("motherTongue", 225);
                put("yearImmigrated", 236);
                put("stop3", 242);
                put("occupation", 253);
                put("industry", 275);
                put("stop4", 304);
                put("remarks", 343);
                put("stop5", 400);
                locked = true;
            }

            @Override
            public Integer put(String key, Integer value)
            {
                if (locked) return value;
                super.put(key, value);
                return value;
            }
        };

        String street;
        String streetNumber;
        String firstName;
        String lastName;
        String relation;
        String ownOrRent;
        String rentValue;
        String sex;
        String age;
        String maritalStatus;
        String firstMarriage;
        String attendSchool;
        String canRead;
        String birthplace;
        String birthplaceDad;
        String birthplaceMom;
        String motherTongue;
        String yearImmigrated;
        String occupation;
        String industry;
        String remarks;
        String comparison;

        @Override
        public String toString() {
            String out = "{\r\n";
            for (Field f: getClass().getDeclaredFields())
            {
                try {
                    if (!Modifier.isStatic(f.getModifiers()))
                        //noinspection StringConcatenationInLoop
                        out += (f.get(this) != null && !f.get(this).equals(".")) ? "\t" + f.getName() + ": " + f.get(this) + ",\r\n" : "";
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return out + "}";
        }

        static Citizen createFrom(String text) throws NoSuchFieldException, IllegalAccessException {
            Citizen out = new Citizen();
            Iterator<String> keys = parseIndices.keySet().iterator();
            String key = keys.next();
            int index = parseIndices.get(key) - 1;
            while (keys.hasNext()) {
                String nextKey = keys.next();
                if (!key.contains("stop"))
                {
                    int toIndex = parseIndices.get(nextKey) - 1;
                    toIndex = Math.min(toIndex, text.length() - 1);
                    Citizen.class.getDeclaredField(key).set(out, text.substring(index, toIndex).trim());
                }
                key = nextKey;
                index = parseIndices.get(key) - 1;
            }
            return out;
        }

        static Citizen switchComparisonToStreet(Citizen citizen)
        {
            citizen.comparison = "street";
            return citizen;
        }

        static Citizen switchComparisonToAge(Citizen citizen)
        {
            citizen.comparison = "age";
            return citizen;
        }

        static Citizen switchComparisonToLastName(Citizen citizen)
        {
            citizen.comparison = "last";
            return citizen;
        }

        static Citizen switchComparisonToFirstName(Citizen citizen)
        {
            citizen.comparison = "first";
            return citizen;
        }

        static Double parseAge(String age)
        {
            int space = age.indexOf(" ");
            int slash = age.indexOf("/");
            double o = Double.parseDouble(age.substring(0, space == -1 || space > age.length() ? age.length() : space));
            if (slash != -1) {
                double f = Double.parseDouble(age.substring(space + 1, slash));
                o += f/12;
            }
            return o;
        }

        @Override
        public int compareTo(Citizen o) {
            if (comparison.equals("first"))
                return firstName.compareTo(o.firstName);
            if (comparison.equals("last"))
                return lastName.compareTo(o.lastName);
            if (comparison.equals("street"))
                return lastName.compareTo(o.lastName);
            if (comparison.equals("age"))
                return parseAge(age).compareTo(parseAge(o.age));
            return 0;
        }
    }

    private final static String fileName = "FedCensus1930_CambriaCountyPA.txt";
    public static void main(String[] args)
    {
        File file = new File(fileName);

        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        Collection<Citizen> collection = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String text;
            while ((text = reader.readLine()) != null)
            {
                if (text.startsWith("17")) {
                    try {
                        collection.add(Citizen.createFrom(text));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(collection.parallelStream());
    }
}
