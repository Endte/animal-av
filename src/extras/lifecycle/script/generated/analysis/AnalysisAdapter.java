/* This file was generated by SableCC (http://www.sablecc.org/). */

package extras.lifecycle.script.generated.analysis;

import java.util.*;

import extras.lifecycle.script.generated.analysis.Analysis;
import extras.lifecycle.script.generated.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    public void caseAScript(AScript node)
    {
        defaultCase(node);
    }

    public void caseASingleStatementSequence(ASingleStatementSequence node)
    {
        defaultCase(node);
    }

    public void caseAStatementSequenceTail(AStatementSequenceTail node)
    {
        defaultCase(node);
    }

    public void caseAAssignStatement(AAssignStatement node)
    {
        defaultCase(node);
    }

    public void caseAExpressiononlyStatement(AExpressiononlyStatement node)
    {
        defaultCase(node);
    }

    public void caseAExpression(AExpression node)
    {
        defaultCase(node);
    }

    public void caseASingleExpression(ASingleExpression node)
    {
        defaultCase(node);
    }

    public void caseAFunctionExpression(AFunctionExpression node)
    {
        defaultCase(node);
    }

    public void caseAArgument(AArgument node)
    {
        defaultCase(node);
    }

    public void caseAArgumentTail(AArgumentTail node)
    {
        defaultCase(node);
    }

    public void caseAIdentifierValueExpression(AIdentifierValueExpression node)
    {
        defaultCase(node);
    }

    public void caseADecimalIntegerLiteralValueExpression(ADecimalIntegerLiteralValueExpression node)
    {
        defaultCase(node);
    }

    public void caseAStringLiteralValueExpression(AStringLiteralValueExpression node)
    {
        defaultCase(node);
    }

    public void caseADecimalIntegerLiteral(ADecimalIntegerLiteral node)
    {
        defaultCase(node);
    }

    public void caseTComment(TComment node)
    {
        defaultCase(node);
    }

    public void caseTWhiteSpace(TWhiteSpace node)
    {
        defaultCase(node);
    }

    public void caseTLeftParenthesis(TLeftParenthesis node)
    {
        defaultCase(node);
    }

    public void caseTRightParenthesis(TRightParenthesis node)
    {
        defaultCase(node);
    }

    public void caseTEq(TEq node)
    {
        defaultCase(node);
    }

    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    public void caseTSemicolon(TSemicolon node)
    {
        defaultCase(node);
    }

    public void caseTDecimalNumeral(TDecimalNumeral node)
    {
        defaultCase(node);
    }

    public void caseTIdentifier(TIdentifier node)
    {
        defaultCase(node);
    }

    public void caseTStringLiteral(TStringLiteral node)
    {
        defaultCase(node);
    }

    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    public void defaultCase(Node node)
    {
        // do nothing
    }
}
