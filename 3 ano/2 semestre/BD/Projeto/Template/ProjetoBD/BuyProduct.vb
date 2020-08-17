Imports System.Data.SqlClient

Public Class BuyProduct
    Dim CMD As SqlCommand
    Dim CN As SqlConnection = New SqlConnection("Data Source = localhost;" &
                                                "Initial Catalog = LojaDesporto; Integrated Security = true;")

    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        NIFTextBox.Text = ""
        ClientsNameTextBox.Text = ""
        WorkersNameTextBox.Text = ""
        UnitsTextBox.Text = ""
    End Sub

    'Client's NIF TextBox (KeyPressed)
    Private Sub TextBox1_KeyPress(sender As Object, e As EventArgs) Handles NIFTextBox.KeyPress
        NumberOnly(e)
    End Sub
    Private Sub NumberOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only numeric characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Nº Units TextBox (KeyPressed)
    Private Sub TextBox5_KeyPress(sender As Object, e As EventArgs) Handles UnitsTextBox.KeyPress
        NumberOnly(e)
    End Sub

    'Confirm Button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        If NIFTextBox.Text.Length <> 9 Then
            MsgBox("Client's NIF must have 9 numbers!", MsgBoxStyle.Information, "ERROR")
        ElseIf UnitsTextBox.Text = "" Then
            MsgBox("Nº units is needed!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim index As Integer = Stores.ProductsDataGridView.CurrentRow.Index
            Dim selectedRow As DataGridViewRow = Stores.ProductsDataGridView.Rows(index)
            Dim storeQuant As Integer = selectedRow.Cells(2).Value

            Dim nif As Integer = NIFTextBox.Text
            Dim drv As DataRowView = CodeComboBox.SelectedItem
            Dim tmp As String = drv.Item("NumFunc").ToString()
            Dim code As Int32 = Convert.ToInt32(tmp)
            Dim units As Integer = Convert.ToInt32(UnitsTextBox.Text)

            If (units > storeQuant) Then
                MsgBox("There's not enough units in the store!", MsgBoxStyle.Information, "ERROR")
            Else
                Stores.BuyProduct(nif, code, units)
                Me.Close()
            End If
        End If
    End Sub

    'Client's NIF TextBox (TextChanged)
    Private Sub TextBox1_TextChanged(sender As Object, e As EventArgs) Handles NIFTextBox.TextChanged
        If (NIFTextBox.Text.Length = 9) Then
            getClient()
        Else
            ClientsNameTextBox.Text = ""
        End If
    End Sub

    Private Sub getClient()
        Dim nif As Integer = NIFTextBox.Text
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Cliente.Nome FROM Projeto.Cliente WHERE Cliente.NIF = @NIF"
        CMD.Parameters.Add("@NIF", SqlDbType.Int)
        CMD.Parameters("@NIF").Value = nif
        CN.Open()
        Dim clientName As Object = CMD.ExecuteScalar()
        If clientName Is Nothing Then
            MsgBox("The client's NIF inserted does not exist!", MsgBoxStyle.Information, "ERROR")
        Else
            ClientsNameTextBox.Text = clientName.ToString
        End If
        CN.Close()
    End Sub

    'Form Loader
    Private Sub BuyProduct_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        Dim index As Integer = Stores.StoresDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = Stores.StoresDataGridView.Rows(index)
        Dim numStore As Integer = selectedRow.Cells(0).Value
        Dim table As New DataTable()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT * FROM Projeto.Funcionario WHERE Funcionario.NumLoja=@Store"
        CMD.Parameters.Add("@Store", SqlDbType.Int)
        CMD.Parameters("@Store").Value = numStore
        CN.Open()

        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(table)
        With CodeComboBox
            .DataSource = table
            .DisplayMember = "NumFunc"
            .ValueMember = "NumFunc"
        End With
        CN.Close()
    End Sub

    'Get Worker's name
    Private Sub CodeComboBox_SelectedIndexChanged(sender As Object, e As EventArgs) Handles CodeComboBox.SelectedIndexChanged

        CN.Close()
        Dim index As Integer = Stores.StoresDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = Stores.StoresDataGridView.Rows(index)
        Dim numStore As Integer = selectedRow.Cells(0).Value

        Dim drv As DataRowView = CodeComboBox.SelectedItem
        Dim tmp As String = drv.Item("NumFunc").ToString()
        Dim code As Int32 = Convert.ToInt32(tmp)

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Funcionario.Nome FROM Projeto.Funcionario WHERE Funcionario.NumFunc = @Code
                          AND Funcionario.NumLoja = @Store"
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Store", SqlDbType.Int)
        CMD.Parameters("@Code").Value = code
        CMD.Parameters("@Store").Value = numStore
        CN.Open()
        Dim workersName As Object = CMD.ExecuteScalar()
        WorkersNameTextBox.Text = workersName.ToString()
        CN.Close()
    End Sub
End Class