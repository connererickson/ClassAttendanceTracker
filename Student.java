/**
 * Authors:           Briggs Richardson, Conner Erickson, Kim Kieu Pham,
 *                    Valeriia Starosek
 * Class ID:          70605 , CSE360 Tues 9:00 AM
 * Assignment:        Final Project
 * File Description:  This file contains the Student class, a basic data type used in organization of the table.
 */

public class Student {
    private String first, last, program, asurite,level, id;

    /**
     * Constructor for Student class
     * @param id id
     * @param first first name
     * @param last last name
     * @param program field of study
     * @param level level of study
     * @param asurite asurite id
     */
    public Student(String id, String first, String last, String program,
            String level, String asurite) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.program = program;
        this.level = level;
        this.asurite = asurite;
    }

    /**
     * Converts object info into a string array.
     * @return a string array with all the object info
     */
    public String[] stringify()
    {
        String[] output = new String[6];
        output[0] = id;
        output[1] = first;
        output[2] = last;
        output[3] = program;
        output[4] = level;
        output[5] = asurite;
        return output;
    }

    public String getID() {
        return asurite;
    }
}
