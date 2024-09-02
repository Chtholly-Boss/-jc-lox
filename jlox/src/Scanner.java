import enums.TokenType;
import types.Token;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static enums.TokenType.*;

public class Scanner {
    private final String src;
    private final List<Token> tokens = new ArrayList<>();

    private int start = 0;
    private int cur = 0;
    private int line = 0;

    private static final Map<String, TokenType> keywords;
    static {
        keywords = new HashMap<>();
        keywords.put("and",    AND);
        keywords.put("class",  CLASS);
        keywords.put("else",   ELSE);
        keywords.put("false",  FALSE);
        keywords.put("for",    FOR);
        keywords.put("fun",    FUN);
        keywords.put("if",     IF);
        keywords.put("nil",    NIL);
        keywords.put("or",     OR);
        keywords.put("print",  PRINT);
        keywords.put("return", RETURN);
        keywords.put("super",  SUPER);
        keywords.put("this",   THIS);
        keywords.put("true",   TRUE);
        keywords.put("var",    VAR);
        keywords.put("while",  WHILE);
    }

    Scanner(String src) {
        this.src = src;
    }

    List<Token> scanTokens(){
        while(!isAtEnd()){
            start = cur;
            scanToken();
        }
        tokens.add(new Token(EOF,"", null,line));
        return tokens;
    }
    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_BRACE); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;

            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            // meaningless symbols
            case '/':
                if (match('/')) {
                    // A comment goes until the end of the line.
                    while (peek() != '\n' && !isAtEnd()) advance();
                } else {
                    addToken(SLASH);
                }
                break;
            case ' ':
            case '\r':
            case '\t':
                // Ignore whitespace.
                break;

            case '\n':
                line++;
                break;

            case '"':
                string();
                break;


            default:
                if (isDigit(c)){
                    number();
                } else if (isAlpha(c)){
                    identifier();
                } else {
                    Lox.error(line,"Unexpected character.");
                }
                break;
        }
    }

    private void addToken(TokenType type, Object literal) {
        String text = src.substring(start, cur);
        tokens.add(new Token(type, text, literal, line));
    }

    private void addToken(TokenType type){
        addToken(type, null);
    }

    private boolean isAtEnd(){
        return cur >= src.length();
    }

    private char advance(){
        return src.charAt(cur++);
    }

    private boolean match(char expected) {
        if(isAtEnd()) return false;
        if(src.charAt(cur) != expected) return false;

        cur++;
        return true;
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return src.charAt(cur);
    }

    private char peekNext() {
        if (cur + 1 >= src.length()) return '\0';
        return src.charAt(cur + 1);
    }


    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }



    private void string() {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated string.");
            return;
        }

        // The closing ".
        advance();

        // Trim the surrounding quotes.
        String value = src.substring(start + 1, cur - 1);
        addToken(STRING, value);
    }

    private void number() {
        while (isDigit(peek())) advance();

        // Look for a fractional part.
        if (peek() == '.' && isDigit(peekNext())) {
            // Consume the "."
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(NUMBER,
                Double.parseDouble(src.substring(start, cur)));
    }

    private void identifier() {
        while (isAlphaNumeric(peek())) advance();
        String text = src.substring(start, cur);
        TokenType type = keywords.get(text);
        if (type == null) type = IDENTIFIER;
        addToken(type);
    }
}
