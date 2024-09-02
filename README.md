# Lox Interpreters
This is my hands-on implementation of jlox/clox.
You can find the book on [Craft-Interpreters](https://craftinginterpreters.com/contents.html)
The Development process is documented here.

## Jlox
### Stage 0: Lex-Parse-Evaluate
This stage corresponding Chapter 4-7 of the book.

#### Setup
Firstly, we need to have some feedback to see the result of our interpreter.
We can implement a REPL (Read-Eval-Print Loop) to do this.

The first thing we need to do is to implement a `runPrompt` method in `Lox` and call it in `main`.

```java
private static void runPrompt() throws IOException {
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(input);

    for(;;){
        System.out.print("> ");
        String line = reader.readLine();
        if (line == null) break;
        run(line);
    }
}
```

When we implement a new feature, we can directly test it by running the program.

#### Lexical Analysis
Token
:   a tuple of a type and a value

The first step is to implement a `Scanner` class to tokenize the input.

```java
public class Scanner {
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
}
```

Scanner takes the source code as input and outputs a list of tokens.

To strengthen the readability of the code, we wrap some operaions in a function:

Global Operations:

* `isAtEnd`: check if the current position is at the end of the source code

Character-Wise Operations:
* `advance`: return the character at the current position and move to the next character
* `peek`: get the current character without moving the position
* `peekNext`: get the next character without moving the position
* `match`: check if the next character matches the given character, and move the position if it does
* `isDigit`: check if the given character is a digit
* `isAlpha`: check if the given character is an alpha character
* `isAlphaNumeric`: check if the given character is an alpha or a digit

Token-Wise Operations:
* `string`: get a string token from the current position
* `number`: get a number token from the current position
* `identifier`: get an identifier token from the current position
  * use a `HashMap` to store the keywords

Then you can implement the `scanTokens` method to tokenize the source code.

```java
public List<Token> scanTokens() {
    while (!isAtEnd()) {
        start = current;
        scanToken();
    }
    tokens.add(new Token(TokenType.EOF, "", null, line));
    return tokens;
}
```

In `scanToken`, we use a **switch-case** to handle different token types. Just need to be carefully move the position and add the token to the list.

Also, you can report error while lexing.

#### Parsing
The next step is to implement a `Parser` class to parse the tokens.

#### Evaluation
The last step is to implement an `Interpreter` class to evaluate the AST.

### Stage 1: Control Flow

### Stage 2: Functions

### Stage 3: Classes


## Clox


## Java Mess
Static Blocks
:   In java, static blocks are used to **initialize the static variables of a class**. They are executed when the class is loaded into memory. Static blocks are **executed only once**, no matter how many objects of the class are created. They are used to initialize the static variables and perform any other static initialization tasks.

```java
class MyClass {
    static int myStaticVar = 0;

    static {
        myStaticVar = 10;
        System.out.println("Static block executed");
    }
}
```

enum
:   An enum is a special "class" that represents **a group of constants** (unchangeable/read-only variables). To define an enum, use the enum keyword (instead of class or interface), and separate the constants with a comma. Enum constants are usually declared as static and final, but this is not required.

```java
public enum TokenType {
    // Single-character tokens.
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, SEMICOLON, SLASH, STAR,

    // One or two character tokens.
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    // Literals.
    IDENTIFIER, STRING, NUMBER,

    // Keywords.
    AND, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF
}
```

    
