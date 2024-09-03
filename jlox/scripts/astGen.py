import os
import argparse

# define the basename
basename = 'Expr'
# define the types
types = [
  'Binary: Expr left, Token operator, Expr right',
  'Grouping: Expr expression',
  'Literal: Object value',
  'Unary: Token operator, Expr right'
]

def defineVisitor(file,basename,types):
  # declare a interface with generic type
  file.write(f'  public interface {basename}Visitor<R> {{\n')
  # for each type, declare a method with generic type
  for type in types:
    # type is a string like "Binary: Expr left, Token operator, Expr right"
    # Get the type name
    type_name = type.split(':')[0]
    # write the method declaration
    file.write(f'    R visit{type_name}{basename}({type_name} {basename.lower()});\n')
  # close the interface
  file.write('  }\n\n')


def defineAst(path, basename, types):
  # create a file with the given path and basename, with postfix .java
  # if exists, overwrite it
  with open(os.path.join(path, basename + '.java'), 'w') as f:
    # write the package declaration
    f.write('package types;\n\n')
    # write the import statements
    f.write('import java.util.List;\n\n')
    # write the class declaration
    f.write(f'public abstract class {basename} {{ \n\n')

    # declare a visit method with generic type
    f.write(f'  public abstract <R> R accept({basename}Visitor<R> visitor);\n\n')


    # Define a visitor interface
    defineVisitor(f,basename,types)

    # for each type, write the class declaration
    for type in types:
      # type is a string like "Binary: Expr left, Token operator, Expr right"
      # Get the type name and the fields
      type_name, fields = type.split(':')
      # split the fields by comma
      fields = fields.split(', ')
      # write the class declaration
      f.write(f'  public static class {type_name} extends {basename} {{\n')
      # write the fields
      for field in fields:
        # remove spaces around
        field = field.strip()
        # split into type and name
        field_type, field_name = field.strip().split(' ')
        # write the field declaration
        f.write(f'    public final {field_type} {field_name};\n')
      # write the constructor
      f.write(f'    public {type_name}({", ".join([f"{field.strip()}" for field in fields])}) {{\n')

      # write the field assignments
      for field in fields:
        # remove spaces around
        field = field.strip()
        # split into type and name
        field_type, field_name = field.split(' ')
        # write the field assignment
        f.write(f'      this.{field_name} = {field_name};\n')
      f.write(f'    }}\n\n')
      # implement the accept method
      f.write(f'    @Override\n')
      f.write(f'    public <R> R accept({basename}Visitor<R> visitor) {{\n')
      f.write(f'      return visitor.visit{type_name}{basename}(this);\n')
      f.write(f'    }}\n\n')

      f.write(f'  }}\n\n')
      
    f.write(f'}}\n\n')
    f.close()


if __name__ == '__main__':
  parser = argparse.ArgumentParser(description='Generate AST classes')
  parser.add_argument('-o', '--output', help='Output path')
  # parse the command line arguments
  args = parser.parse_args()
  # get the output path
  # if not provided, use the current directory
  output_path = args.output
  if output_path is None:
    output_path = os.getcwd()
  # call defineAst
  defineAst(output_path, basename, types)
  