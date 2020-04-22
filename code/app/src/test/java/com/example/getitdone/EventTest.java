package com.example.getitdone;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import com.example.getitdone.Models.Event;

import java.util.Date;

public class EventTest {

    Event sut = new Event();
    @Test
    public void EventMemoryDateTest(){
        Date t = new Date();

        sut.setDate(t);

        assertEquals(sut.getDate(), t);

    }
    @Test
    public void EventMemoryFavoriteTest(){

        sut.setFavorited(true);

        assertEquals(sut.isFavorited(), true);
    }
    @Test
    public void EventMemoryTitleTest(){

        sut.setTitle("Title");

        assertEquals(sut.getTitle(), "Title");
    }
    @Test
    public void EventMemoryTasksTest(){

        sut.setTask1("First Task");
        sut.setTask2("Second Task");
        sut.setTask3("Third Task");

        assertEquals(sut.getTask1(), "First Task");
        assertEquals(sut.getTask2(), "Second Task");
        assertEquals(sut.getTask3(), "Third Task");
    }
}
