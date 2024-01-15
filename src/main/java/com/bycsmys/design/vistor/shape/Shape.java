package com.bycsmys.design.vistor.shape;


import com.bycsmys.design.vistor.Visitor;

public interface Shape {
    void move(int x, int y);
    void draw();
    String accept(Visitor visitor);
}