Imports System.Data.SqlClient

Public Class Deliveries
    Dim Check As Date
    Dim Check2 As Date

    Dim CMD As SqlCommand
    Dim CN As SqlConnection = New SqlConnection("Data Source = localhost;" &
                                                "Initial Catalog = LojaDesporto; Integrated Security = true;")

    Public Sub loadDeliveries()
        Dim adapter As New SqlDataAdapter("SELECT Transporte.IDTransporte AS IDTransporte, Data AS Date, Destino AS Destination FROM Projeto.Transporte", CN)
        Dim table As New DataTable()
        adapter.Fill(table)

        With DeliveriesDataGridView
            .DataSource = table
            Dim scroll As VScrollBar = DeliveriesDataGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
            If (scroll.Visible) Then
                .Columns(0).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.2
                .Columns(1).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.2
                .Columns(2).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.6
            Else
                .Columns(0).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.2
                .Columns(1).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.2
                .Columns(2).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.6
            End If
        End With
    End Sub

    Public Function CheckCode(code As Integer)
        Dim codecheck As Object

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT * FROM Projeto.Artigo WHERE Artigo.Codigo=@code;"
        CMD.Parameters.Add("@code", SqlDbType.Int)
        CMD.Parameters("@code").Value = code
        CN.Open()
        codecheck = CMD.ExecuteScalar()
        CN.Close()
        Return codecheck
    End Function

    Public Function CheckID(id As Integer)
        Dim idcheck As Object

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT * FROM Projeto.Transporte WHERE Transporte.IDTransporte=@id;"
        CMD.Parameters.Add("@id", SqlDbType.Int)
        CMD.Parameters("@id").Value = id
        CN.Open()
        idcheck = CMD.ExecuteScalar()
        CN.Close()
        Return idcheck
    End Function

    Public Function CheckProduct(code As Integer, store As Integer)
        Dim productcheck As Integer

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Artigo_Loja.QuantArtigos FROM Projeto.Artigo_Loja WHERE Artigo_Loja.NumLoja=@store AND Artigo_Loja.Codigo=@code;"
        CMD.Parameters.Add("@code", SqlDbType.Int)
        CMD.Parameters.Add("@store", SqlDbType.Int)
        CMD.Parameters("@code").Value = code
        CMD.Parameters("@store").Value = store
        CN.Open()
        productcheck = CMD.ExecuteScalar()
        CN.Close()
        Return productcheck
    End Function

    Public Function CheckStore(store As Integer)
        Dim storecheck As Object

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT * FROM Projeto.Loja WHERE Loja.NumLoja=@store;"
        CMD.Parameters.Add("@store", SqlDbType.Int)
        CMD.Parameters("@store").Value = store
        CN.Open()
        storecheck = CMD.ExecuteScalar()
        CN.Close()
        Return storecheck
    End Function

    Public Function CheckTransQuant(id As Integer, code As Integer)
        Dim quantTrans As Object

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Artigo_Transporte.QuantArtigos FROM Projeto.Artigo_Transporte WHERE Artigo_Transporte.IDTransporte=@id AND Artigo_Transporte.Codigo=@code;"
        CMD.Parameters.Add("@id", SqlDbType.Int)
        CMD.Parameters.Add("@code", SqlDbType.Int)
        CMD.Parameters("@id").Value = id
        CMD.Parameters("@code").Value = code
        CN.Open()
        quantTrans = CMD.ExecuteScalar()
        CN.Close()
        Return quantTrans
    End Function

    Public Sub FilterData(valueToSearch As String)
        Dim search As String = TextBoxSearch.Text
        Dim table2 As New DataTable()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT * FROM  Projeto.Transporte WHERE IDTransporte like '%' + @search + '%'"

        CMD.Parameters.Add("@search", SqlDbType.VarChar, 40)
        CMD.Parameters("@search").Value = search
        CN.Open()

        Dim adapter2 As New SqlDataAdapter(CMD)
        adapter2.Fill(table2)

        With DeliveriesDataGridView
            .DataSource = table2
            Dim scroll As VScrollBar = DeliveriesDataGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
            If (scroll.Visible) Then
                .Columns(0).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.2
                .Columns(1).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.2
                .Columns(2).Width = (DeliveriesDataGridView.Size.Width - 20) * 0.6
            Else
                .Columns(0).Width = (DeliveriesDataGridView.Size.Width - 3) * 0.2
                .Columns(1).Width = (DeliveriesDataGridView.Size.Width - 3) * 0.2
                .Columns(2).Width = (DeliveriesDataGridView.Size.Width - 3) * 0.6
            End If
        End With
        CN.Close()

        DeliveriesDataGridView.DataSource = table2

    End Sub

    'Deliveries DataGridView
    Private Sub DataGridview1_CellClick(sender As Object, e As DataGridViewCellEventArgs) Handles DeliveriesDataGridView.CellClick
        Dim index As Integer = e.RowIndex
        If (index = -1) Then
        Else
            Dim selectedRow As DataGridViewRow = DeliveriesDataGridView.Rows(index)
            Dim ID As String = selectedRow.Cells(0).Value.ToString

            TextBoxStore.Text = ""
            Button2.Enabled = True
            Button3.Enabled = True
            Button1.Enabled = False
            Button5.Enabled = False
            TextBoxDate.Enabled = False
            TextBoxAmount.Enabled = False
            TextBoxDest.Enabled = False
            TextBoxStore.Enabled = False
            TextBoxID.Text = selectedRow.Cells(0).Value.ToString
            Dim tmp As Date = selectedRow.Cells(1).Value
            TextBoxDate.Text = tmp.ToString("dd/MM/yyyy")
            TextBoxDest.Text = selectedRow.Cells(2).Value.ToString
            Label3.Enabled = True

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "SELECT Artigo_Transporte.Codigo FROM Projeto.Artigo_Transporte Where Artigo_Transporte.IDTransporte = @id"
            CMD.Parameters.Add("@id", SqlDbType.VarChar, 40)
            CMD.Parameters("@id").Value = ID
            CN.Open()

            Dim code As String = CMD.ExecuteScalar().ToString
            TextBoxCode.Text = code
            CN.Close()

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "SELECT Artigo_Transporte.QuantArtigos FROM Projeto.Artigo_Transporte Where Artigo_Transporte.IDTransporte = @id"
            CMD.Parameters.Add("@id", SqlDbType.VarChar, 40)
            CMD.Parameters("@id").Value = ID
            CN.Open()

            Dim type As String = CMD.ExecuteScalar().ToString
            TextBoxAmount.Text = type

            If CN.State = ConnectionState.Open Then
                CN.Close()
            End If
        End If
    End Sub

    'Edit Button
    Private Sub Button3_Click(sender As Object, e As EventArgs) Handles Button3.Click
        TextBoxDate.Enabled = True
        TextBoxAmount.Enabled = True
        TextBoxDest.Enabled = True
        TextBoxStore.Enabled = True
        Button1.Enabled = True
        Button3.Enabled = False
        Button5.Enabled = True
    End Sub

    'ProductCode TextBox
    Private Sub TextBoxCode_KeyPress(sender As Object, e As EventArgs) Handles TextBoxCode.KeyPress
        NumberOnly(e)
    End Sub
    Private Sub NumberOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only numeric characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Store Number TextBox
    Private Sub TextBoxStore_KeyPress(sender As Object, e As EventArgs) Handles TextBoxStore.KeyPress
        NumberOnly(e)
    End Sub

    Private Sub CheckDate(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Or Asc(e.KeyChar) = 47 Or Asc(e.KeyChar) = 8 Then
        Else
            e.Handled = True
            MsgBox("Only numeric characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Amount TextBox
    Private Sub TextBoxAmount_KeyPress(sender As Object, e As EventArgs) Handles TextBoxAmount.KeyPress
        NumberOnly(e)
    End Sub

    'Destination TextBox
    Private Sub TextBoxDest_KeyPress(sender As Object, e As EventArgs) Handles TextBoxDest.KeyPress
        LettersOnly(e)
    End Sub
    Private Sub LettersOnly(ByVal e As System.Windows.Forms.KeyPressEventArgs)
        If (Asc(e.KeyChar) >= 48 And Asc(e.KeyChar) <= 57) Then
            e.Handled = True
            MsgBox("Only alphabetic characteres are allowed!", MsgBoxStyle.Information, "ERROR")
        End If
    End Sub

    'Save Button
    Private Sub Button1_Click(sender As Object, e As EventArgs) Handles Button1.Click
        If TextBoxCode.Text = "" Or TextBoxDate.Text = "" Or TextBoxDest.Text = "" Or TextBoxAmount.Text = "" Or TextBoxStore.Text = "" Then
            MsgBox("Some textboxes are empty!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim productcheck As Object
            Dim storecheck As Object
            Dim aux As Integer = Convert.ToInt32(TextBoxCode.Text)
            Dim aux2 As Integer = Convert.ToInt32(TextBoxStore.Text)
            storecheck = CheckStore(aux2)
            productcheck = CheckProduct(aux, aux2)
            Dim aux3 As Integer = Convert.ToInt32(TextBoxAmount.Text)
            Dim aux4 As Integer = Convert.ToInt32(TextBoxID.Text)
            Dim quantTrans As Integer = CheckTransQuant(aux4, aux)
            If (TextBoxDate.Text.Length <> 10) Then
                MsgBox("Date must be in format DD/MM/YYYY!", MsgBoxStyle.Information, "ERROR")
            Else
                Check = Convert.ToDateTime(TextBoxDate.Text)
            End If
            If Check <= Date.Now Then
                MsgBox("Date must be after today!", MsgBoxStyle.Information, "ERROR")
            ElseIf TextBoxDate.Text(2) <> "/" Or TextBoxDate.Text(5) <> "/" Or TextBoxDate.Text(8) <> "2" Then
                MsgBox("Date must be in format DD/MM/YYYY!", MsgBoxStyle.Information, "ERROR")
            ElseIf storecheck Is Nothing Then
                MsgBox("The store with that number does not exist!", MsgBoxStyle.Information, "ERROR")
                TextBoxStore.Text = ""
            ElseIf (productcheck + quantTrans) - aux3 < 0 Then
                MsgBox("The are not enough units to complete the order!", MsgBoxStyle.Information, "ERROR")
            Else
                TextBoxDate.Enabled = False
                TextBoxAmount.Enabled = False
                TextBoxStore.Enabled = False
                TextBoxDest.Enabled = False
                Button1.Enabled = False
                Button3.Enabled = True
                Button5.Enabled = False
                Dim data As Date = TextBoxDate.Text
                Dim dest As String = TextBoxDest.Text
                Dim code As Integer = TextBoxCode.Text
                Dim quant As Integer = TextBoxAmount.Text
                Dim store As Integer = TextBoxStore.Text
                updateDelivery(data, dest, code, quant, store)
                MessageBox.Show("Delivery edited successfully")
            End If
        End If
    End Sub

    'Cancel Button
    Private Sub Button5_Click(sender As Object, e As EventArgs) Handles Button5.Click
        TextBoxDate.Enabled = False
        TextBoxAmount.Enabled = False
        TextBoxCode.Enabled = False
        TextBoxDest.Enabled = False
        Button5.Enabled = False
        Button3.Enabled = True
        Button1.Enabled = False
    End Sub

    'ProductCode2 TextBox
    Private Sub TextBoxCode2_KeyPress(sender As Object, e As EventArgs) Handles TextBoxCode2.KeyPress
        NumberOnly(e)
    End Sub

    'Store Number2 TextBox
    Private Sub TextBoxStore2_KeyPress(sender As Object, e As EventArgs) Handles TextBoxStore2.KeyPress
        NumberOnly(e)
    End Sub

    'Date2 TextBox
    Private Sub TextBoxDate2_KeyPress(sender As Object, e As EventArgs) Handles TextBoxDate2.KeyPress
        CheckDate(e)
    End Sub

    'Amount2 TextBox
    Private Sub TextBoxAmount2_KeyPress(sender As Object, e As EventArgs) Handles TextBoxAmount2.KeyPress
        NumberOnly(e)
    End Sub

    'Destination2 TextBox
    Private Sub TextBoxDest2_KeyPress(sender As Object, e As EventArgs) Handles TextBoxDest2.KeyPress
        LettersOnly(e)
    End Sub

    'ID2 TextBox
    Private Sub TextBoxID2_KeyPress(sender As Object, e As EventArgs) Handles TextBoxID2.KeyPress
        NumberOnly(e)
    End Sub

    'Add Button
    Private Sub Button4_Click(sender As Object, e As EventArgs) Handles Button4.Click
        If TextBoxCode2.Text = "" Or TextBoxDate2.Text = "" Or TextBoxDest2.Text = "" Or TextBoxAmount2.Text = "" Or TextBoxID2.Text = "" Or TextBoxStore2.Text = "" Then
            MsgBox("Some textboxes are empty!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim codecheck As Object
            Dim aux As Integer = Convert.ToInt32(TextBoxCode2.Text)
            codecheck = CheckCode(aux)
            Dim idcheck As Object
            Dim aux2 As Integer = Convert.ToInt32(TextBoxID2.Text)
            idcheck = CheckID(aux2)
            Dim productcheck As Integer
            Dim aux3 As Integer = Convert.ToInt32(TextBoxStore2.Text)
            Dim aux4 As Integer = Convert.ToInt32(TextBoxAmount2.Text)
            productcheck = CheckProduct(aux, aux3)
            Dim storecheck As Object
            storecheck = CheckStore(aux3)
            If (TextBoxDate2.Text.Length <> 10) Then
                MsgBox("Date must be in format DD/MM/YYYY!", MsgBoxStyle.Information, "ERROR")
            Else
                Check2 = Convert.ToDateTime(TextBoxDate2.Text)
            End If
            If TextBoxCode2.Text.Length <> 6 Then
                MsgBox("Product Code must have 6 numbers!", MsgBoxStyle.Information, "ERROR")
            ElseIf TextBoxID2.Text.Length <> 6 Then
                MsgBox("Transport ID must have 6 numbers!", MsgBoxStyle.Information, "ERROR")
            ElseIf Check2 <= Date.Now Then
                MsgBox("Date must be after today!", MsgBoxStyle.Information, "ERROR")
            ElseIf TextBoxDate2.Text(2) <> "/" Or TextBoxDate2.Text(5) <> "/" Or TextBoxDate2.Text(6) <> "2" Then
                MsgBox("Date must be in format DD/MM/YYYY!", MsgBoxStyle.Information, "ERROR")
            ElseIf Not idcheck Is Nothing Then
                MsgBox("The delivery with that code already exist!", MsgBoxStyle.Information, "ERROR")
            ElseIf storecheck Is Nothing Then
                MsgBox("The store with that number does not exist!", MsgBoxStyle.Information, "ERROR")
            ElseIf codecheck Is Nothing Then
                MsgBox("The product with that code does not exist!", MsgBoxStyle.Information, "ERROR")
            ElseIf productcheck < aux4 Then
                MsgBox("The are not enough units to complete the order!", MsgBoxStyle.Information, "ERROR")
            Else
                Dim id As Integer = TextBoxID2.Text
                Dim data As Date = TextBoxDate2.Text
                Dim dest As String = TextBoxDest2.Text
                Dim code As Integer = TextBoxCode2.Text
                Dim quant As Integer = TextBoxAmount2.Text
                Dim store As Integer = TextBoxStore2.Text
                addDelivery(id, data, dest, code, quant, store)
                TextBoxID2.Text = ""
                TextBoxDate2.Text = ""
                TextBoxDest2.Text = ""
                TextBoxCode2.Text = ""
                TextBoxAmount2.Text = ""
                TextBoxStore2.Text = ""
                MessageBox.Show("Delivery added successfully")
            End If
        End If
    End Sub

    Private Sub TextBoxSearch_TextChanged(sender As Object, e As EventArgs) Handles TextBoxSearch.TextChanged
        FilterData("")
    End Sub

    Private Sub Label3_Click(sender As Object, e As EventArgs) Handles Label3.Click
        Label3.Enabled = False
        Label3.Visible = False
    End Sub

    Private Sub addDelivery(ByVal id As Integer, ByVal data As Date, ByVal dest As String, ByVal code As Integer, ByVal quant As Integer, ByVal store As Integer)
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Add_Delivery @id, @data, @dest, @code, @quant,@store"
        CMD.Parameters.Add("@id", SqlDbType.Int)
        CMD.Parameters.Add("@data", SqlDbType.Date)
        CMD.Parameters.Add("@dest", SqlDbType.VarChar, 40)
        CMD.Parameters.Add("@code", SqlDbType.Int)
        CMD.Parameters.Add("@quant", SqlDbType.Int)
        CMD.Parameters.Add("@store", SqlDbType.Int)
        CMD.Parameters("@id").Value = id
        CMD.Parameters("@data").Value = data
        CMD.Parameters("@dest").Value = dest
        CMD.Parameters("@code").Value = code
        CMD.Parameters("@quant").Value = quant
        CMD.Parameters("@store").Value = store
        CN.Open()
        CMD.ExecuteScalar()
        loadDeliveries()
        CN.Close()
    End Sub

    Private Sub updateDelivery(ByVal data As Date, ByVal dest As String, ByVal code As Integer, ByVal quant As Integer, ByVal store As Integer)
        Dim index As Integer = DeliveriesDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = DeliveriesDataGridView.Rows(index)
        Dim id As Integer = selectedRow.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Remove_Delivery @id, @store"
        CMD.Parameters.Add("@id", SqlDbType.Int)
        CMD.Parameters.Add("@store", SqlDbType.Int)
        CMD.Parameters("@id").Value = id
        CMD.Parameters("@store").Value = store
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Add_Delivery @id, @data, @dest, @code, @quant, @store"
        CMD.Parameters.Add("@id", SqlDbType.Int)
        CMD.Parameters.Add("@data", SqlDbType.Date)
        CMD.Parameters.Add("@dest", SqlDbType.VarChar, 40)
        CMD.Parameters.Add("@code", SqlDbType.Int)
        CMD.Parameters.Add("@quant", SqlDbType.Int)
        CMD.Parameters.Add("@store", SqlDbType.Int)
        CMD.Parameters("@id").Value = id
        CMD.Parameters("@data").Value = data
        CMD.Parameters("@dest").Value = dest
        CMD.Parameters("@code").Value = code
        CMD.Parameters("@quant").Value = quant
        CMD.Parameters("@store").Value = store
        CN.Open()
        CMD.ExecuteScalar()
        loadDeliveries()
        CN.Close()
    End Sub

    'Remove Delivery
    Private Sub Button2_Click(sender As Object, e As EventArgs) Handles Button2.Click
        Dim index As Integer = DeliveriesDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = DeliveriesDataGridView.Rows(index)
        Dim num As Integer = selectedRow.Cells(0).Value
        Dim selectstore As New StoreSelect
        selectstore.StartPosition = FormStartPosition.CenterScreen
        selectstore.ShowDialog()
        CN.Open()
        loadDeliveries()
        CN.Close()
        TextBoxID.Text = ""
        TextBoxDate.Text = ""
        TextBoxAmount.Text = ""
        TextBoxDest.Text = ""
        TextBoxCode.Text = ""
        TextBoxStore.Text = ""
        DeliveriesDataGridView.ClearSelection()
    End Sub
End Class