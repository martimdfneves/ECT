from django import template

register = template.Library()

@register.filter
def get_type(value):
    return type(value)

@register.filter
def get_content(value):
    a=False
    if type(value) == str:
        if len(value)==0:
            a = True
    elif type(value) == dict:
        if len(value)==0:
            a= True
    elif type(value) == list:
        if len(value)==0:
            a= True
    elif type(value) == NoneType:
        a = True
    elif type(value) == None:
        a = True
    return a