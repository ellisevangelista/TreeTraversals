package evangelista_syntaxt;

import java.io.*;
import java.util.Stack;

class FirstStack {
    private Node[] a;
    private int top, m;
    public FirstStack(int max){
        m = max;
        a = new Node[m];
        top = -1;
    }
    public void push(Node key){
        a[++top] = key;
    }
    public Node pop(){
        return (a[top--]);
    } 
    public boolean isEmpty(){
        return (top == -1);
    }
}
class SecondStack {
    private char[] a;
    private int top, m;
    public SecondStack(int max){
        m = max;
        a = new char[m];
        top = -1;
    }
    public void push(char key){
        a[++top] = key;
    }
    public char pop(){
        return (a[top--]);
    }
    public boolean isEmpty(){
        return (top == -1);
    }
} 
class ConvertInfixToTraversal{
    private SecondStack s;
    private String input;
    private String output = "";
    public ConvertInfixToTraversal(String X){
        input = X;
        s = new SecondStack(X.length());
    }
    public String convertToPost(){
        for (int i = 0; i < input.length(); i++){
            char ch = input.charAt(i);
            switch (ch)
            {
                case '+':
                case '-':
                    isOperator(ch, 1);
                    break;
                case '*':
                case '/':
                    isOperator(ch, 2);
                    break;
                case '(':
                    s.push(ch);
                    break;
                case ')':
                    withParenthesis();
                    break;
                default:
                    output = output + ch;
            }
        }
        while (!s.isEmpty())
            output = output + s.pop();
        return output;
    }
    private void isOperator(char optr, int prec1){
        while (!s.isEmpty())
        {
            char opTop = s.pop();
            if (opTop == '(')
            {
                s.push(opTop);
                break;
            } else
            {
                int prec2;
                if (opTop == '+' || opTop == '-')
                    prec2 = 1;
                else
                    prec2 = 2;
                if (prec2 < prec1)
                {
                    s.push(opTop);
                    break;
                } else
                    output = output + opTop;
            }
        }
        s.push(optr);
    }
 
    private void withParenthesis()
    {
        while (!s.isEmpty())
        {
            char ch = s.pop();
            if (ch == '(')
                break;
            else
                output = output + ch;
        }
    }
}
class Tree
{
    private Node root;
    public Tree()
    {
        root = null;
    }
 
    public void insert(String s)
    {
        ConvertInfixToTraversal c = new ConvertInfixToTraversal(s);
        s = c.convertToPost();
        FirstStack stk = new FirstStack(s.length());
        s = s + "#";
        int i = 0;
        char input = s.charAt(i);
        Node newNode;
        while (input != '#')
        {
            if (input >= '0' && input <= '9' || input >= 'A'
                    && input <= 'Z' || input >= 'a' && input <= 'z')
            {
                newNode = new Node(null, input, null);
                stk.push(newNode);
            } else if (input == '+' || input == '-' || input == '/'
                    || input == '*')
            {
                Node ptr1 = stk.pop();
                Node ptr2 = stk.pop();
                newNode = new Node(ptr2, input, ptr1);
                stk.push(newNode);
            }
            input = s.charAt(++i);
        }
        root = stk.pop();
    }
 
    public void traverse(int type)
    {
        switch (type)
        {
            case 1:
                System.out.print("\n");
                System.out.print("Preorder Traversal:    ");
                preOrderTrv(root);
                break;
            case 2:
                System.out.print("Inorder Traversal:     ");
                inOrderTrv(root);
                break;
            case 3:
                System.out.print("Postorder Traversal:   ");
                postOrderTrv(root);
                System.out.print("\n");
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }
 
    private void preOrderTrv(Node localRoot)
    {
        if (localRoot != null)
        {
            localRoot.displayNode();
            preOrderTrv(localRoot.leftChild);
            preOrderTrv(localRoot.rightChild);
        }
    }
 
    private void inOrderTrv(Node localRoot)
    {
        if (localRoot != null)
        {
            inOrderTrv(localRoot.leftChild);
            localRoot.displayNode();
            inOrderTrv(localRoot.rightChild);
        }
    }
 
    private void postOrderTrv(Node localRoot)
    {
        if (localRoot != null)
        {
            postOrderTrv(localRoot.leftChild);
            postOrderTrv(localRoot.rightChild);
            localRoot.displayNode();
        }
    }
    public void syntaxTree()
    {
        Stack syntax = new Stack();
        syntax.push(root); 
        int emptyLeaf = 32;
        boolean isRowEmpty = false;
        System.out.println("-----------------------------------------------------------");
        while(isRowEmpty==false)
        {
            Stack syntaxStack = new Stack();
            isRowEmpty = true;
            for(int j = 0; j < emptyLeaf; j++)
                System.out.print(' ');
            while(syntax.isEmpty()==false)
            {
                Node temp = (Node)syntax.pop();
                if(temp != null)
                {
                    System.out.print(temp.infix);
                    syntaxStack.push(temp.leftChild);
                    syntaxStack.push(temp.rightChild);
                    if(temp.leftChild != null ||temp.rightChild != null)
                        isRowEmpty = false;
                }
                else
                {
                    System.out.print("--");
                    syntaxStack.push(null);
                    syntaxStack.push(null);
                }
                for(int j = 0; j < emptyLeaf * 2-2; j++)
                    System.out.print(' ');
            }
            System.out.println();
            emptyLeaf /= 2;
            while(syntaxStack.isEmpty()==false)
                            syntax.push(syntaxStack.pop());
        }
		System.out.println("-----------------------------------------------------------");
		}
    }
class Node
{
    public char infix;
    public Node leftChild;
    public Node rightChild;
 
    public Node(Node left, char x, Node right)
    {
        infix = x;
        leftChild = left;
        rightChild = right;
    }
	public void displayNode()
	{
		System.out.print(infix);
	}
}
public class Evangelista_SyntaxT
{
    public static void main(String args[]) throws IOException
    {
        String ch = "y";
        DataInputStream inp = new DataInputStream(System.in);
        while (ch.equals("y"))
        {
            Tree t1 = new Tree();
            System.out.println("Input infix expression: ");
            String a = inp.readLine();
            t1.insert(a);
            t1.traverse(1);
            System.out.println("");
            t1.traverse(2);
            System.out.println("");
            t1.traverse(3);
            System.out.println("");
            System.out.println("Syntax Tree: ");
            t1.syntaxTree();
            System.out.print("\n");
            System.out.print("Press y to continue ");
            ch = inp.readLine();
        }
    }
}
