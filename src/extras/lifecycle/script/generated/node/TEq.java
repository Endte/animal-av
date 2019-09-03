/* This file was generated by SableCC (http://www.sablecc.org/). */

package extras.lifecycle.script.generated.node;

import extras.lifecycle.script.generated.node.Switch;
import extras.lifecycle.script.generated.node.TEq;
import extras.lifecycle.script.generated.node.Token;
import extras.lifecycle.script.generated.analysis.*;

@SuppressWarnings("nls")
public final class TEq extends Token
{
    public TEq(String text)
    {
        setText(text);
    }

    public TEq(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TEq(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTEq(this);
    }
}
