package lox.types;

public abstract class Expr {

  public abstract <R> R accept(ExprVisitor<R> visitor);

  public interface ExprVisitor<R> {
    R visitAssignExpr(Assign expr);
    R visitBinaryExpr(Binary expr);
    R visitGroupingExpr(Grouping expr);
    R visitLiteralExpr(Literal expr);
    R visitLogicalExpr(Logical expr);
    R visitUnaryExpr(Unary expr);
    R visitVariableExpr(Variable expr);
  }

  public static class Assign extends Expr {
    public final Token name;
    public final Expr value;
    public Assign(Token name, Expr value) {
      this.name = name;
      this.value = value;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
      return visitor.visitAssignExpr(this);
    }

  }

  public static class Binary extends Expr {
    public final Expr left;
    public final Token operator;
    public final Expr right;
    public Binary(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
      return visitor.visitBinaryExpr(this);
    }

  }

  public static class Grouping extends Expr {
    public final Expr expression;
    public Grouping(Expr expression) {
      this.expression = expression;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
      return visitor.visitGroupingExpr(this);
    }

  }

  public static class Literal extends Expr {
    public final Object value;
    public Literal(Object value) {
      this.value = value;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
      return visitor.visitLiteralExpr(this);
    }

  }

  public static class Logical extends Expr {
    public final Expr left;
    public final Token operator;
    public final Expr right;
    public Logical(Expr left, Token operator, Expr right) {
      this.left = left;
      this.operator = operator;
      this.right = right;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
      return visitor.visitLogicalExpr(this);
    }

  }

  public static class Unary extends Expr {
    public final Token operator;
    public final Expr right;
    public Unary(Token operator, Expr right) {
      this.operator = operator;
      this.right = right;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
      return visitor.visitUnaryExpr(this);
    }

  }

  public static class Variable extends Expr {
    public final Token name;
    public Variable(Token name) {
      this.name = name;
    }

    @Override
    public <R> R accept(ExprVisitor<R> visitor) {
      return visitor.visitVariableExpr(this);
    }

  }

}

