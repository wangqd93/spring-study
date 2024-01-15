package com.bycsmys.design.vistor;

import com.bycsmys.design.vistor.shape.Circle;
import com.bycsmys.design.vistor.shape.Dot;
import com.bycsmys.design.vistor.shape.Rectangle;

public interface Visitor {
    String visitDot(Dot dot);

    String visitCircle(Circle circle);

    String visitRectangle(Rectangle rectangle);

    String visitCompoundGraphic(CompoundShape cg);
}