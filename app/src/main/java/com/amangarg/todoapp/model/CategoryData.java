package com.amangarg.todoapp.model;

public class CategoryData {
    int CategoryID;
    String CategoryDetails, CategoryNotes;

    public int getCategoryID() {
        return CategoryID;
    }

    public void setToDoID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryDetails() {
        return CategoryDetails;
    }

    public void setCategoryDetails(String categoryDetails) {
        CategoryDetails = categoryDetails;
    }

    public String getCategoryNotes() {
        return CategoryNotes;
    }

    public void setCategoryNotes(String categoryNotes) {
        CategoryNotes = categoryNotes;
    }

    @Override
    public String toString() {
        return "ToDoData {id-" + CategoryID + ", taskDetails-" + CategoryDetails + ", notes-" + CategoryNotes + "}";
    }

}

