from django import forms

class CommentForm(forms.Form):
    your_comment = forms.CharField(widget=forms.Textarea)
