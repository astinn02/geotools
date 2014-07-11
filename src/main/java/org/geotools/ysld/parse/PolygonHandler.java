package org.geotools.ysld.parse;

import org.geotools.styling.*;
import org.opengis.filter.expression.Expression;
import org.yaml.snakeyaml.events.ScalarEvent;

public class PolygonHandler extends SymbolizerHandler<PolygonSymbolizer> {

    public PolygonHandler(Rule rule, Factory factory) {
        super(rule, factory.style.createPolygonSymbolizer(), factory);
    }

    @Override
    public void scalar(ScalarEvent evt, YamlParseContext context) {
        String val = evt.getValue();
        if ("stroke".equals(val)) {
            context.push(new StrokeHandler(factory) {
                @Override
                protected void stroke(Stroke stroke) {
                    sym.setStroke(stroke);
                }
            });
        }
        else if ("fill".equals(val)) {
            context.push(new FillHandler(factory) {
                @Override
                protected void fill(Fill fill) {
                    sym.setFill(fill);
                }
            });
        }
        else if ("offset".equals(val)) {
            context.push(new ExpressionHandler(factory) {
                @Override
                protected void expression(Expression expr) {
                    sym.setPerpendicularOffset(expr);
                }
            });
        }
        else if ("displacement".equals(val)) {
            context.push(new DisplacementHandler(factory) {
                @Override
                protected void displace(Displacement displacement) {
                    sym.setDisplacement(displacement);
                }
            });
        }
        else {
            super.scalar(evt, context);
        }
    }
}
