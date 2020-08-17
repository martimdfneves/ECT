Imports System.Data.SqlClient

Public Class Clients
    Dim flag As Boolean
    Dim CMD As SqlCommand
    Dim CN As SqlConnection = New SqlConnection("Data Source = localhost;" &
                                                "Initial Catalog = LojaDesporto; Integrated Security = true;")

    'Client's Insert Button
    Private Sub Button7_Click(sender As Object, e As EventArgs) Handles Button7.Click
        If NIFTextBox.Text.Length <> 9 Then
            MsgBox("Client's NIF Must Have 9 Numbers!", MsgBoxStyle.Information, "ERROR")
        ElseIf NameTextBox.Text.Equals("") Then
            MsgBox("Please Insert Client's Name!", MsgBoxStyle.Information, "ERROR")
        ElseIf PhoneTextBox.Text.Length <> 9 Then
            MsgBox("Client's Phone Number Must Have 9 Numbers!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim NIF As Integer = NIFTextBox.Text
            Dim name As String = NameTextBox.Text.ToString
            Dim address As String = AddressTextBox.Text.ToString()
            Dim phone As Integer = PhoneTextBox.Text
            Dim NIFbool As Boolean = checkNIF(NIF)
            Dim PhoneBool As Boolean = checkPhone(phone)
            If (NIFbool = True And PhoneBool = True) Then
                addClient(NIF, address, name, phone)
            ElseIf (NIFbool = False And PhoneBool = True) Then
                MsgBox("The NIF inserted already exists!", MsgBoxStyle.Information, "ERROR")
                Me.flag = False
            ElseIf (NIFbool = True And PhoneBool = False) Then
                MsgBox("The Phone number inserted already exists!", MsgBoxStyle.Information, "ERROR")
                Me.flag = False
            ElseIf (NIFbool = False And PhoneBool = False) Then
                MsgBox("The NIF and Phone number inserted already exists!", MsgBoxStyle.Information, "ERROR")
                Me.flag = False
            End If
            If (Me.flag = True) Then
                NIFTextBox.Text = ""
                NameTextBox.Text = ""
                AddressTextBox.Text = ""
                PhoneTextBox.Text = ""
                MsgBox("Client inserted sucessfully!", MsgBoxStyle.Information, "Information")
            Else
                NIFTextBox.Text = NIFTextBox.Text
                NameTextBox.Text = NameTextBox.Text
                AddressTextBox.Text = AddressTextBox.Text
                PhoneTextBox.Text = PhoneTextBox.Text
            End If
            PurchasedProductsGridView.DataSource = Nothing
            ReturnedProductsGridView.DataSource = Nothing
            Button5.Enabled = False
            Button6.Enabled = False
        End If
    End Sub

    'Client's NIF TextBox
    Private Sub TextBox1_KeyPress(sender As Object, e As EventArgs) Handles NIFTextBox.KeyPress
        NumberOnly(e)
    End Sub
    Private Sub NumberOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only Numeric Characteres are Allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Client's Name TextBox
    Private Sub TextBox2_KeyPress(sender As Object, e As EventArgs) Handles NameTextBox.KeyPress
        LettersOnly(e)
    End Sub
    Private Sub LettersOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Then
            e.Handled = True
            MsgBox("Only Alphabetic Characteres are Allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Client's Phone Number TextBox
    Private Sub TextBox4_KeyPress(sender As Object, e As EventArgs) Handles PhoneTextBox.KeyPress
        NumberOnly(e)
    End Sub

    'Edit Button
    Private Sub Button6_Click(sender As Object, e As EventArgs) Handles Button6.Click
        Dim EditClient As New EditClient
        EditClient.StartPosition = FormStartPosition.CenterScreen
        EditClient.ShowDialog()
    End Sub

    'To load Clients DataGridView
    Public Sub loadClients()
        CN.Close()
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT NIF, Nome AS Name, Morada AS Address, NumTelem AS Phone FROM Projeto.Cliente"
        CN.Open()
        Dim ds As New DataSet()
        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(ds)

        With ClientsDataGridView
            .DataSource = ds.Tables(0)
            .Columns(0).Width = 65
            .Columns(1).Width = 100
            .Columns(2).Width = 119
            .Columns(3).Width = 65
            .ClearSelection()
        End With
        CN.Close()
    End Sub

    'Clients Search Bar
    Public Sub FilterData(valueToSearch As String)
        Dim search As String = TextBoxSearch.Text
        Dim table As New DataTable()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT NIF, Nome AS Name, Morada AS Address, NumTelem AS Phone FROM Projeto.Cliente 
                           WHERE CONCAT(Nome, NIF) like '%' + @search + '%'"
        CMD.Parameters.Add("@search", SqlDbType.VarChar, 40)
        CMD.Parameters("@search").Value = search
        CN.Open()

        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(table)

        With ClientsDataGridView
            .DataSource = table
            Dim scroll As VScrollBar = ClientsDataGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
            If (scroll.Visible) Then
                .Columns(0).Width = (ClientsDataGridView.Size.Width - 20) * 0.2
                .Columns(1).Width = (ClientsDataGridView.Size.Width - 20) * 0.29
                .Columns(2).Width = (ClientsDataGridView.Size.Width - 20) * 0.31
                .Columns(3).Width = (ClientsDataGridView.Size.Width - 20) * 0.2
            Else
                .Columns(0).Width = (ClientsDataGridView.Size.Width - 3) * 0.2
                .Columns(1).Width = (ClientsDataGridView.Size.Width - 3) * 0.29
                .Columns(2).Width = (ClientsDataGridView.Size.Width - 3) * 0.31
                .Columns(3).Width = (ClientsDataGridView.Size.Width - 3) * 0.2
            End If
        End With
        CN.Close()
    End Sub

    'Purchased Products DataGridView
    Private Sub ClientsDataGridView_CellContentClick(sender As Object, e As DataGridViewCellEventArgs) Handles ClientsDataGridView.CellClick
        Dim index As Integer = e.RowIndex
        'To not crash when user clicks in the header
        If (index = -1) Then
        Else
            Dim selectedRow As DataGridViewRow = ClientsDataGridView.Rows(index)
            Dim NIF As String = selectedRow.Cells(0).Value.ToString()

            Button5.Enabled = True
            Button6.Enabled = True

            Dim ds As New DataSet()

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "SELECT * FROM Projeto.PurchasedProductsPerClient(@NIF);"
            CMD.Parameters.Add("@NIF", SqlDbType.Int)
            CMD.Parameters("@NIF").Value = NIF
            CN.Open()

            Dim adapter As New SqlDataAdapter(CMD)
            adapter.Fill(ds)

            With PurchasedProductsGridView
                .DataSource = ds.Tables(0)
                Dim scroll As VScrollBar = PurchasedProductsGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
                If (scroll.Visible) Then
                    .Columns(0).Width = (PurchasedProductsGridView.Size.Width - 20) * 0.14
                    .Columns(1).Width = (PurchasedProductsGridView.Size.Width - 20) * 0.39
                    .Columns(2).Width = (PurchasedProductsGridView.Size.Width - 20) * 0.13
                    .Columns(3).Width = (PurchasedProductsGridView.Size.Width - 20) * 0.16
                    .Columns(4).Width = (PurchasedProductsGridView.Size.Width - 20) * 0.18
                Else
                    .Columns(0).Width = 69
                    .Columns(1).Width = 193
                    .Columns(2).Width = 64
                    .Columns(3).Width = 83
                    .Columns(4).Width = 93
                End If
                .ClearSelection()
            End With
            CN.Close()

            'Returned Products
            Dim ds2 As New DataSet()

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "SELECT * FROM Projeto.ReturnedProductsPerClient(@NIF);"
            CMD.Parameters.Add("@NIF", SqlDbType.Int)
            CMD.Parameters("@NIF").Value = NIF
            CN.Open()

            Dim adapter2 As New SqlDataAdapter(CMD)
            adapter2.Fill(ds2)

            With ReturnedProductsGridView
                .DataSource = ds2.Tables(0)
                Dim scroll As VScrollBar = ReturnedProductsGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
                If (scroll.Visible) Then
                    .Columns(0).Width = (ReturnedProductsGridView.Size.Width - 20) * 0.14
                    .Columns(1).Width = (ReturnedProductsGridView.Size.Width - 20) * 0.39
                    .Columns(2).Width = (ReturnedProductsGridView.Size.Width - 20) * 0.13
                    .Columns(3).Width = (ReturnedProductsGridView.Size.Width - 20) * 0.16
                    .Columns(4).Width = (ReturnedProductsGridView.Size.Width - 20) * 0.18
                Else
                    .Columns(0).Width = 69
                    .Columns(1).Width = 193
                    .Columns(2).Width = 64
                    .Columns(3).Width = 83
                    .Columns(4).Width = 93
                End If
                .ClearSelection()
            End With

            If CN.State = ConnectionState.Open Then
                CN.Close()
            End If
        End If
    End Sub

    'To add a new Client
    Private Sub addClient(ByVal NIF As Integer, ByVal address As String, ByVal name As String, ByVal phone As Integer)
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "Projeto.Add_newClient"
        CMD.CommandType = CommandType.StoredProcedure
        CMD.Parameters.Add("@NIF", SqlDbType.BigInt)
        CMD.Parameters.Add("@Address", SqlDbType.VarChar, 40)
        CMD.Parameters.Add("@Name", SqlDbType.VarChar, 20)
        CMD.Parameters.Add("@Phone", SqlDbType.BigInt)
        CMD.Parameters("@NIF").Value = NIF
        CMD.Parameters("@Address").Value = address
        CMD.Parameters("@Name").Value = name
        CMD.Parameters("@Phone").Value = phone
        CN.Open()
        CMD.ExecuteScalar()
        loadClients()
        CN.Close()
        Me.flag = True
    End Sub

    'To check if NIF already exists or not
    Private Function checkNIF(ByVal nif As Integer) As Boolean
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT COUNT(*) FROM Projeto.Cliente WHERE Cliente.NIF=@NIF"
        CMD.Parameters.Add("@NIF", SqlDbType.Int)
        CMD.Parameters("@NIF").Value = nif
        CN.Open()
        Dim count As Integer = CMD.ExecuteScalar()
        CN.Close()
        If (count > 0) Then
            Return False
        Else
            Return True
        End If
    End Function

    'To check if phone number already exists or not
    Public Function checkPhone(ByVal phone As Integer) As Boolean
        Dim count As Integer
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT COUNT(*) FROM Projeto.Cliente WHERE Cliente.NumTelem=@Phone"
        CMD.Parameters.Add("@Phone", SqlDbType.BigInt)
        CMD.Parameters("@Phone").Value = phone
        CN.Open()
        count = CMD.ExecuteScalar()
        CN.Close()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT COUNT(*) FROM Projeto.Funcionario WHERE Funcionario.NumTelem=@Phone"
        CMD.Parameters.Add("@Phone", SqlDbType.BigInt)
        CMD.Parameters("@Phone").Value = phone
        CN.Open()
        count = count + CMD.ExecuteScalar()
        CN.Close()

        If (count > 0) Then
            Return False
        Else
            Return True
        End If
    End Function

    'Remove Client Button
    Private Sub Button5_Click(sender As Object, e As EventArgs) Handles Button5.Click
        Dim result As DialogResult = MessageBox.Show("Do you really want to delete the client selected?",
                                                     "Information", MessageBoxButtons.YesNo)

        If (result = DialogResult.Yes) Then
            Dim index As Integer = ClientsDataGridView.CurrentRow.Index
            Dim selectedRow As DataGridViewRow = ClientsDataGridView.Rows(index)
            Dim nif As Integer = selectedRow.Cells(0).Value

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "EXEC Projeto.Remove_Client @NIF"
            CMD.Parameters.Add("@NIF", SqlDbType.Int)
            CMD.Parameters("@NIF").Value = nif
            CN.Open()
            CMD.ExecuteScalar()
            loadClients()
            CN.Close()
            PurchasedProductsGridView.DataSource = Nothing
            ReturnedProductsGridView.DataSource = Nothing
        End If
    End Sub

    'Update Address
    Public Sub updateAddress(ByVal newAddress As String)
        Dim index As Integer = ClientsDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = ClientsDataGridView.Rows(index)
        Dim nif As Integer = selectedRow.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.UpdateAddress @NIF, @Address"
        CMD.Parameters.Add("@NIF", SqlDbType.Int)
        CMD.Parameters.Add("@Address", SqlDbType.VarChar, 40)
        CMD.Parameters("@NIF").Value = nif
        CMD.Parameters("@Address").Value = newAddress
        CN.Open()
        CMD.ExecuteScalar()
        loadClients()
        CN.Close()
    End Sub

    'Update Phone
    Public Sub updatePhone(ByVal newPhone As Integer)
        Dim index As Integer = ClientsDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = ClientsDataGridView.Rows(index)
        Dim nif As Integer = selectedRow.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.UpdatePhone @NIF, @Phone"
        CMD.Parameters.Add("@NIF", SqlDbType.Int)
        CMD.Parameters.Add("@Phone", SqlDbType.Int)
        CMD.Parameters("@NIF").Value = nif
        CMD.Parameters("@Phone").Value = newPhone
        CN.Open()
        CMD.ExecuteScalar()
        loadClients()
        CN.Close()
    End Sub

    Private Sub TextBoxSearch_TextChanged(sender As Object, e As EventArgs) Handles TextBoxSearch.TextChanged
        FilterData("")
    End Sub

    Private Sub Label2_Click(sender As Object, e As EventArgs) Handles Label2.Click
        Label2.Enabled = False
        Label2.Visible = False
    End Sub
End Class