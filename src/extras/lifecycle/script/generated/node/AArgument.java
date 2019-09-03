/* This file was generated by SableCC (http://www.sablecc.org/). */

package extras.lifecycle.script.generated.node;

import java.util.*;

import extras.lifecycle.script.generated.node.AArgument;
import extras.lifecycle.script.generated.node.Node;
import extras.lifecycle.script.generated.node.PArgument;
import extras.lifecycle.script.generated.node.PArgumentTail;
import extras.lifecycle.script.generated.node.PValueExpression;
import extras.lifecycle.script.generated.node.Switch;
import extras.lifecycle.script.generated.analysis.*;

@SuppressWarnings("nls")
public final class AArgument extends PArgument
{
    private PValueExpression _valueExpression_;
    private final LinkedList<PArgumentTail> _argumentTail_ = new LinkedList<PArgumentTail>();

    public AArgument()
    {
        // Constructor
    }

    public AArgument(
        @SuppressWarnings("hiding") PValueExpression _valueExpression_,
        @SuppressWarnings("hiding") List<PArgumentTail> _argumentTail_)
    {
        // Constructor
        setValueExpression(_valueExpression_);

        setArgumentTail(_argumentTail_);

    }

    @Override
    public Object clone()
    {
        return new AArgument(
            cloneNode(this._valueExpression_),
            cloneList(this._argumentTail_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAArgument(this);
    }

    public PValueExpression getValueExpression()
    {
        return this._valueExpression_;
    }

    public void setValueExpression(PValueExpression node)
    {
        if(this._valueExpression_ != null)
        {
            this._valueExpression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._valueExpression_ = node;
    }

    public LinkedList<PArgumentTail> getArgumentTail()
    {
        return this._argumentTail_;
    }

    public void setArgumentTail(List<PArgumentTail> list)
    {
        this._argumentTail_.clear();
        this._argumentTail_.addAll(list);
        for(PArgumentTail e : list)
        {
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._valueExpression_)
            + toString(this._argumentTail_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._valueExpression_ == child)
        {
            this._valueExpression_ = null;
            return;
        }

        if(this._argumentTail_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._valueExpression_ == oldChild)
        {
            setValueExpression((PValueExpression) newChild);
            return;
        }

        for(ListIterator<PArgumentTail> i = this._argumentTail_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PArgumentTail) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
