package lox.types;

import java.util.List;

public abstract class Stmt { 

  public abstract <R> R accept(StmtVisitor<R> visitor);

  public interface StmtVisitor<R> {
    R visitExpressionStmt(Expression stmt);
    R visitPrintStmt(Print stmt);
  }

  public static class Expression extends Stmt {
    public final Expr expression;
    public Expression(Expr expression) {
      this.expression = expression;
    }

    @Override
    public <R> R accept(StmtVisitor<R> visitor) {
      return visitor.visitExpressionStmt(this);
    }

  }

  public static class Print extends Stmt {
    public final Expr expression;
    public Print(Expr expression) {
      this.expression = expression;
    }

    @Override
    public <R> R accept(StmtVisitor<R> visitor) {
      return visitor.visitPrintStmt(this);
    }

  }

}

