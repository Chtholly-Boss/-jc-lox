package lox.types;

import java.util.List;

public abstract class Stmt { 

  public abstract <R> R accept(StmtVisitor<R> visitor);

  public interface StmtVisitor<R> {
    R visitBlockStmt(Block stmt);
    R visitExpressionStmt(Expression stmt);
    R visitPrintStmt(Print stmt);
    R visitVarStmt(Var stmt);
    R visitIfStmt(If stmt);
    R visitWhileStmt(While stmt);
  }

  public static class Block extends Stmt {
    public final List<Stmt> statements;
    public Block(List<Stmt> statements) {
      this.statements = statements;
    }

    @Override
    public <R> R accept(StmtVisitor<R> visitor) {
      return visitor.visitBlockStmt(this);
    }

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

  public static class Var extends Stmt {
    public final Token name;
    public final Expr initializer;
    public Var(Token name, Expr initializer) {
      this.name = name;
      this.initializer = initializer;
    }

    @Override
    public <R> R accept(StmtVisitor<R> visitor) {
      return visitor.visitVarStmt(this);
    }

  }

  public static class If extends Stmt {
    public final Expr condition;
    public final Stmt thenBranch;
    public final Stmt elseBranch;
    public If(Expr condition, Stmt thenBranch, Stmt elseBranch) {
      this.condition = condition;
      this.thenBranch = thenBranch;
      this.elseBranch = elseBranch;
    }

    @Override
    public <R> R accept(StmtVisitor<R> visitor) {
      return visitor.visitIfStmt(this);
    }

  }

  public static class While extends Stmt {
    public final Expr condition;
    public final Stmt body;
    public While(Expr condition, Stmt body) {
      this.condition = condition;
      this.body = body;
    }

    @Override
    public <R> R accept(StmtVisitor<R> visitor) {
      return visitor.visitWhileStmt(this);
    }

  }

}

