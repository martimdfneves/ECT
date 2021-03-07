from lxml import etree
import argparse, sys

def print_elems(elem, text=""):
    elem_contents = text + str(elem) + " " + str(elem.attrib) + " " + str(elem.text)
    elem_contents = elem_contents.replace("{}", "").replace("\n","")
    print(elem_contents)             # print element
    if len(elem.getchildren()) != 0:    # if they exist, print children
        for e in elem:
            print_elems(e, text + "\t")

def menu():
    print("*** MENU ***")
    print("1. Ler XML")
    print("Validar XML")
    print("Mostrar Info Curso")
    print("Sair")
    op=int(input("digite a opção"))
    if op==1:
        ler()
    elif op==2:
        validar()
    elif op==3:
        mostrar()
    else:
        sys.exit(0)

def ler():
    xml_file = etree.parse(args['xml'])

def validar():
    xml_file = etree.parse(args['xml'])
    print(xsd.validate(xml_file))

def mostrar():
    xml_file = etree.parse(args['xml'])
    root = xml_file.getroot()
    print_elems(root)

menu()