Imports System.Data.SqlClient

Public Class AddProduct
    Dim CMD As SqlCommand
    Dim CN As SqlConnection = New SqlConnection("Data Source = localhost;" &
                                                "Initial Catalog = LojaDesporto; Integrated Security = true;")

    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        CodeTextBox.Text = ""
        NameTextBox.Text = ""
        PriceTextBox.Text = ""
        TypeTextBox.Text = ""
        UnitsTextBox.Text = ""
    End Sub

    'Price TextBox
    Private Sub TextBox3_KeyPress(sender As Object, e As EventArgs) Handles PriceTextBox.KeyPress
        CheckPrice(e)
    End Sub
    Private Sub CheckPrice(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 44 Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only numeric characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'To allow "," character only once
    Private Sub TextBox3_TextChanged(sender As Object, e As EventArgs) Handles PriceTextBox.TextChanged
        Dim cont As Integer = 0
        For Each c As Char In PriceTextBox.Text
            If (c = ",") Then
                cont += 1
            End If
        Next
        If (cont > 1) Then
            MsgBox("The character ',' is allowed only one time!", MsgBoxStyle.Information, "ERROR")
            PriceTextBox.Text = PriceTextBox.Text.Substring(0, PriceTextBox.TextLength - 1)
        End If
    End Sub

    'Code TextBox
    Private Sub TextBox1_KeyPress(sender As Object, e As EventArgs) Handles CodeTextBox.KeyPress
        NumberOnly(e)
    End Sub
    Private Sub NumberOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only numeric characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Type TextBox
    Private Sub TextBox4_KeyPress(sender As Object, e As EventArgs) Handles TypeTextBox.KeyPress
        LettersOnly(e)
    End Sub
    Private Sub LettersOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Then
            e.Handled = True
            MsgBox("Only alphabetic characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Num.Units TextBox
    Private Sub TextBox5_KeyPress(sender As Object, e As EventArgs) Handles UnitsTextBox.KeyPress
        NumberOnly(e)
    End Sub

    'Confirm Button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        If CodeTextBox.Text.Length <> 6 Then
            MsgBox("Code number must have 6 numbers!", MsgBoxStyle.Information, "ERROR")
        ElseIf Not (TypeTextBox.Text.Equals("Acessórios") Or TypeTextBox.Text.Equals("Vestuário") Or TypeTextBox.Text.Equals("Calçado")) Then
            MsgBox("Only 'Acessórios', 'Calçado' and 'Vestuário' exists in products type!", MsgBoxStyle.Information, "ERROR")
        ElseIf NameTextBox.Text = "" Or PriceTextBox.Text = "" Or UnitsTextBox.Text = "" Then
            MsgBox("Some textboxes are empty!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim name As String = NameTextBox.Text.ToString
            Dim price As Decimal = PriceTextBox.Text
            Dim code As Integer = CodeTextBox.Text
            Dim type As String = TypeTextBox.Text.ToString
            Dim units As Integer = UnitsTextBox.Text.ToString
            Dim flag As Integer = checkNewProduct(code, price, name, type)
            If (flag = 1) Then
                Stores.addProduct(name, price, code, type, units)
                Me.Close()
            Else
                MsgBox("Some information about the product inserted is wrong!", MsgBoxStyle.Information, "ERROR")
            End If
        End If
    End Sub

    'To check if product exists
    Private Function checkNewProduct(ByVal code As Integer, ByVal price As Decimal, ByVal name As String, ByVal type As String) As Integer
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Projeto.CheckNewProdut(@Code, @Price, @Name, @Type)"
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Price", SqlDbType.Decimal)
        CMD.Parameters.Add("@Name", SqlDbType.VarChar, 40)
        CMD.Parameters.Add("@Type", SqlDbType.VarChar, 20)
        CMD.Parameters("@Code").Value = code
        CMD.Parameters("@Price").Value = price
        CMD.Parameters("@Name").Value = name
        CMD.Parameters("@Type").Value = type
        CN.Open()
        Dim flag As Integer = CMD.ExecuteScalar()
        CN.Close()
        Return flag
    End Function
End Class