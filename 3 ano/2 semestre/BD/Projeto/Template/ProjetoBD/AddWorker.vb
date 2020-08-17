Imports System.Data.SqlClient

Public Class AddWorker
    Dim CMD As SqlCommand
    Dim CN As SqlConnection = New SqlConnection("Data Source = localhost;" &
                                                "Initial Catalog = LojaDesporto; Integrated Security = true;")

    Private Function CheckNum(num As Integer)
        Dim numcheck As Object
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT * FROM Projeto.Funcionario WHERE Funcionario.NumFunc=@num;"
        CMD.Parameters.Add("@num", SqlDbType.Int)
        CMD.Parameters("@num").Value = num
        CN.Open()
        numcheck = CMD.ExecuteScalar()
        CN.Close()
        Return numcheck
    End Function

    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        TextBox1.Text = ""
        TextBox2.Text = ""
        TextBox4.Text = ""
        TextBox5.Text = ""
    End Sub

    'NumFunc TextBox
    Private Sub TextBox1_KeyPress(sender As Object, e As EventArgs) Handles TextBox1.KeyPress
        NumberOnly(e)
    End Sub
    Private Sub NumberOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only numeric characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'PhoneNumber TextBox
    Private Sub TextBox5_KeyPress(sender As Object, e As EventArgs) Handles TextBox5.KeyPress
        NumberOnly(e)
    End Sub

    'Name TextBox
    Private Sub TextBox4_KeyPress(sender As Object, e As EventArgs) Handles TextBox4.KeyPress
        LettersOnly(e)
    End Sub
    Private Sub LettersOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Then
            e.Handled = True
            MsgBox("Only alphabetic characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Address TextBox
    Private Sub TextBox2_KeyPress(sender As Object, e As EventArgs) Handles TextBox2.KeyPress
        LettersOnly(e)
    End Sub

    'Confirm Button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        Dim idcheck As Object = CheckNum(Convert.ToInt32(TextBox1.Text))
        If TextBox1.Text.Length <> 6 Then
            MsgBox("FuncNum must have 6 numbers!", MsgBoxStyle.Information, "ERROR")
        ElseIf TextBox5.Text.Length <> 9 Then
            MsgBox("Phone number must have 9 numbers!", MsgBoxStyle.Information, "ERROR")
        ElseIf TextBox2.Text = "" Or TextBox4.Text = "" Then
            MsgBox("Some textboxes are empty!", MsgBoxStyle.Information, "ERROR")
        ElseIf Not idcheck Is Nothing Then
            MsgBox("The worker with that number already exist!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim num As Integer = TextBox1.Text
            Dim morada As String = TextBox2.Text
            Dim nome As String = TextBox4.Text
            Dim phone As Integer = TextBox5.Text
            If (Clients.checkPhone(phone) = False) Then
                MsgBox("The phone number inserted already exists!", MsgBoxStyle.Information, "ERROR")
            Else
                Workers.addWorker(num, morada, nome, phone)
                Me.Close()
                MessageBox.Show("Worker added successfully")
            End If
        End If
    End Sub

End Class