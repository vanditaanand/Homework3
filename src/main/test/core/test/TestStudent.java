package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;
import core.api.impl.Admin;
import core.api.impl.Student;
import core.api.impl.Instructor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
    private IAdmin admin;
    private IStudent student;
    private IInstructor instructor;

    @Before
    public void setup() {
        this.admin = new Admin();
        this.admin.createClass("Test",2017,"Instructor", 1);
        this.student = new Student();
        this.instructor = new Instructor();
    }

    @Test
    public void testRegisterForClass() { 
    	// making sure RegisterForClass() works
    	
        this.student.registerForClass("stud","Test",2017);
        assertTrue(this.student.isRegisteredFor("stud","Test",2017));
    }

    @Test
    public void testRegisterClassIsFull() { 
    	// if class is full students should not be able to register
    	
        IStudent student2 = new Student();
        this.student.registerForClass("stud1", "Test", 2017);
        student2.registerForClass("stud2", "Test", 2017);
        assertFalse(student2.isRegisteredFor("stud2", "Test", 2017));
    }

    @Test
    public void testRegisterClassDoesNotExist() { 
    	// if class does not exist, students should not be able to register
    	
        this.student.registerForClass("stud","Test2",2017);
        assertFalse(this.student.isRegisteredFor("stud","Test2",2017));
    }

    @Test
    public void testDropClass() { 
    	// making sure DropClass() works
    	
        this.student.registerForClass("stud", "Test",2017);
        assertTrue(this.student.isRegisteredFor("stud","Test",2017));

        this.student.dropClass("stud", "Test", 2017);
        assertFalse(this.student.isRegisteredFor("stud", "Test", 2017));
    }

    @Test
    public void testSubmitHomework() { 
    	// test that submitHomework() works
    	
        this.student.registerForClass("stud", "Test", 2017);
        this.instructor.addHomework("Instructor", "Test", 2017, "hw");
        this.student.submitHomework("stud", "hw", "answer", "Test", 2017);
        assertTrue(this.student.hasSubmitted("stud", "hw", "Test", 2017));
    }

    @Test
    public void testSubmitHomeworkNotRegisteredInClass() { 
    	// can not submit homework if they are not registered in class
    	
        this.instructor.addHomework("Instructor", "Test", 2017, "hw");
        this.student.submitHomework("stud", "hw", "answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("stud", "hw", "Test", 2017));
    }

    @Test
    public void testSubmitHomeworkNotAssigned() { 
    	// can not submit homework if it wasn't assigned
    	
        this.student.submitHomework("stud", "hw", "answer", "Test", 2017);
        assertFalse(this.student.hasSubmitted("stud", "hw", "Test", 2017));
    }

    @Test
    public void testSubmitHomeworkWrongYear() { 
    	// should not be able to submit homework to a class for the wrong year
    	
        this.admin.createClass("Test2",2018,"Instructor",1);
        this.instructor.addHomework("Instructor", "Test2", 2018, "hw");
        this.student.registerForClass("stud", "Test2", 2018);
        this.student.submitHomework("stud", "hw", "answer", "Test2", 2018);
        assertFalse(this.student.hasSubmitted("stud", "hw", "Test2", 2018));
    }
}
