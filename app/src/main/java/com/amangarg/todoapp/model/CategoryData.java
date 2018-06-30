package com.amangarg.todoapp.model;

public class CategoryData {
    int CategoryID;
    String CategoryTitle;

    public void setCategoryId(int categoryID) {
        CategoryID = categoryID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryTitle(String categoryTitle) {
        CategoryTitle = categoryTitle;
    }

    public String getCategoryTitle() {
        return CategoryTitle;
    }

    @Override
    public String toString() {
        return "CategoryData {categoryId-" + CategoryID + ", categoryTitle-" + CategoryTitle + "}";
    }

}

