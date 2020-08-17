Imports System.Data.SqlClient

Public Class Stores
    Dim CMD As SqlCommand
    Dim CN As SqlConnection = New SqlConnection("Data Source = localhost;" &
                                                "Initial Catalog = LojaDesporto; Integrated Security = true;")

    'Add Store Button
    Private Sub Button12_Click(sender As Object, e As EventArgs) Handles Button12.Click
        Dim addStore As New AddStore
        addStore.StartPosition = FormStartPosition.CenterScreen
        addStore.ShowDialog()
    End Sub

    'Add Product Button
    Private Sub Button7_Click(sender As Object, e As EventArgs) Handles Button7.Click
        Dim addProduct As New AddProduct
        addProduct.StartPosition = FormStartPosition.CenterScreen
        addProduct.ShowDialog()
    End Sub

    'Buy Product Button 
    Private Sub Button20_Click(sender As Object, e As EventArgs) Handles Button20.Click
        Dim code As String = TextBoxCode.Text.ToString
        If (code.Equals("")) Then
            MsgBox("Please select a product to buy!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim buyProduct As New BuyProduct
            buyProduct.StartPosition = FormStartPosition.CenterScreen
            buyProduct.ShowDialog()
        End If
    End Sub

    'Return Product Button
    Private Sub Button22_Click(sender As Object, e As EventArgs) Handles Button22.Click
        Dim returnProduct As New ReturnProduct
        returnProduct.StartPosition = FormStartPosition.CenterScreen
        returnProduct.ShowDialog()
    End Sub

    'Add Product Button (Warehouse)
    Private Sub Button9_Click(sender As Object, e As EventArgs) Handles Button9.Click
        Dim addWarehouseProduct As New addWarehouseProduct
        addWarehouseProduct.StartPosition = FormStartPosition.CenterScreen
        addWarehouseProduct.ShowDialog()
    End Sub

    'Add Warehouse Button
    Private Sub Button14_Click(sender As Object, e As EventArgs) Handles Button14.Click
        Dim addWarehouse As New AddWarehouse
        addWarehouse.StartPosition = FormStartPosition.CenterScreen
        addWarehouse.ShowDialog()
    End Sub

    'Remove Product Button
    Private Sub Button6_Click(sender As Object, e As EventArgs) Handles Button6.Click
        Dim code As String = TextBoxCode.Text.ToString
        If (code.Equals("")) Then
            MsgBox("Please select a product to remove!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim removeProduct As New RemoveProduct
            removeProduct.StartPosition = FormStartPosition.CenterScreen
            removeProduct.ShowDialog()
        End If
    End Sub

    'Remove Product Button (Warehouse)
    Private Sub Button8_Click(sender As Object, e As EventArgs) Handles Button8.Click
        Dim code As String = TextBoxCode2.Text.ToString
        If (code.Equals("")) Then
            MsgBox("Please select a product to remove!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim removeWarehouseProduct As New RemoveWarehouseProduct
            removeWarehouseProduct.StartPosition = FormStartPosition.CenterScreen
            removeWarehouseProduct.ShowDialog()
        End If
    End Sub

    'Add to the Store Button
    Private Sub Button5_Click(sender As Object, e As EventArgs) Handles Button5.Click
        Dim code As String = TextBoxCode2.Text.ToString
        If (code.Equals("")) Then
            MsgBox("Please select a product to move to the store!", MsgBoxStyle.Information, "ERROR")
        Else
            Dim addToTheStore As New addToStore
            addToTheStore.StartPosition = FormStartPosition.CenterScreen
            addToTheStore.ShowDialog()
        End If
    End Sub

    'Load Stores DataGridView
    Public Sub loadStores()
        Dim adapter As New SqlDataAdapter("SELECT NumLoja AS Number, Nome AS Name FROM Projeto.Loja", CN)
        Dim table As New DataTable()
        adapter.Fill(table)

        With StoresDataGridView
            .DataSource = table
            Dim scroll As VScrollBar = StoresDataGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
            If (scroll.Visible) Then
                .Columns(0).Width = (StoresDataGridView.Size.Width - 20) * 0.2
                .Columns(1).Width = (StoresDataGridView.Size.Width - 20) * 0.8
            Else
                .Columns(0).Width = (StoresDataGridView.Size.Width - 3) * 0.2
                .Columns(1).Width = (StoresDataGridView.Size.Width - 3) * 0.8
            End If
            .ClearSelection()
        End With
    End Sub

    'Store's Products Search Bar
    Public Sub FilterData(valueToSearch As String)
        Dim numStore As String = StoresDataGridView.CurrentRow.Cells(0).Value.ToString
        Dim search As String = TextBoxSearch.Text
        Dim table As New DataTable()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Name, Price, Units FROM FilterProducts
                           WHERE Name like '%' + @search + '%' AND NumLoja like '%' + @store + '%'"
        CMD.Parameters.Add("@search", SqlDbType.VarChar, 40)
        CMD.Parameters("@search").Value = search
        CMD.Parameters.Add("@store", SqlDbType.VarChar, 3)
        CMD.Parameters("@store").Value = numStore
        CN.Open()

        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(table)

        With ProductsDataGridView
            .DataSource = table
            Dim scroll As VScrollBar = ProductsDataGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
            If (scroll.Visible) Then
                .Columns(0).Width = (ProductsDataGridView.Size.Width - 20) * 0.69
                .Columns(1).Width = (ProductsDataGridView.Size.Width - 20) * 0.16
                .Columns(2).Width = (ProductsDataGridView.Size.Width - 20) * 0.15
            Else
                .Columns(0).Width = (ProductsDataGridView.Size.Width - 3) * 0.69
                .Columns(1).Width = (ProductsDataGridView.Size.Width - 3) * 0.16
                .Columns(2).Width = (ProductsDataGridView.Size.Width - 3) * 0.15
            End If
            .ClearSelection()
        End With
        CN.Close()
    End Sub

    'Warehouses's Products Search Bar
    Public Sub FilterData2(valueToSearch As String)
        Dim armazem As String = WarehousesDataGridView.CurrentRow.Cells(0).Value.ToString
        Dim search As String = TextBoxSearch2.Text
        Dim table As New DataTable()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Name, Price, Units FROM FilterWarehouseProducts
                           WHERE Name like '%' + @search + '%' AND Armazem like '%' + @armazem + '%'"
        CMD.Parameters.Add("@search", SqlDbType.VarChar, 40)
        CMD.Parameters("@search").Value = search
        CMD.Parameters.Add("@armazem", SqlDbType.VarChar, 3)
        CMD.Parameters("@armazem").Value = armazem
        CN.Open()

        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(table)

        With WarehousesProductsDataGridView
            .DataSource = table
            Dim scroll As VScrollBar = WarehousesProductsDataGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
            If (scroll.Visible) Then
                .Columns(0).Width = (WarehousesProductsDataGridView.Size.Width - 20) * 0.69
                .Columns(1).Width = (WarehousesProductsDataGridView.Size.Width - 20) * 0.16
                .Columns(2).Width = (WarehousesProductsDataGridView.Size.Width - 20) * 0.15
            Else
                .Columns(0).Width = (WarehousesProductsDataGridView.Size.Width - 3) * 0.69
                .Columns(1).Width = (WarehousesProductsDataGridView.Size.Width - 3) * 0.16
                .Columns(2).Width = (WarehousesProductsDataGridView.Size.Width - 3) * 0.15
            End If
            .ClearSelection()
        End With
        CN.Close()

    End Sub

    Private Sub TextBoxSearch_TextChanged(sender As Object, e As EventArgs) Handles TextBoxSearch.TextChanged
        FilterData("")
    End Sub

    Private Sub TextBoxSearch2_TextChanged(sender As Object, e As EventArgs) Handles TextBoxSearch2.TextChanged
        FilterData2("")
    End Sub

    'Stores DataGridView
    Private Sub DataGridview1_CellClick(sender As Object, e As DataGridViewCellEventArgs) Handles StoresDataGridView.CellClick
        Dim index As Integer = e.RowIndex
        'To not crash when user clicks in the header
        If (index = -1) Then
        Else
            Dim selectedRow As DataGridViewRow = StoresDataGridView.Rows(index)
            Dim numStore As String = selectedRow.Cells(0).Value.ToString

            Button9.Enabled = False
            Button8.Enabled = False
            Button5.Enabled = False
            Button13.Enabled = False

            'Store Prducts
            loadStoresProducts(numStore)

            'Warehouses
            loadWarehouses(numStore)
        End If
    End Sub

    'Stores' products DataGridView
    Private Sub DataGridView2_CellContentClick(sender As Object, e As DataGridViewCellEventArgs) Handles ProductsDataGridView.CellClick
        Dim index As Integer = e.RowIndex
        'To not crash when user clicks in the header
        If (index = -1) Then
        Else
            Dim selectedRow As DataGridViewRow = ProductsDataGridView.Rows(index)
            Dim productName As String = selectedRow.Cells(0).Value.ToString

            TextBoxName.Text = selectedRow.Cells(0).Value.ToString
            TextBoxPrice.Text = selectedRow.Cells(1).Value.ToString
            TextBoxUnits.Text = selectedRow.Cells(2).Value.ToString

            Button6.Enabled = True
            Button20.Enabled = True

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "SELECT Artigo.Codigo FROM Projeto.Artigo Where Artigo.Nome = @productName"
            CMD.Parameters.Add("@productName", SqlDbType.VarChar, 40)
            CMD.Parameters("@productName").Value = productName
            CN.Open()

            Dim code As String = CMD.ExecuteScalar().ToString
            TextBoxCode.Text = code
            CN.Close()

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "Select Artigo.Categoria FROM Projeto.Artigo Where Artigo.Nome = @productName"
            CMD.Parameters.Add("@productName", SqlDbType.VarChar, 40)
            CMD.Parameters("@productName").Value = productName
            CN.Open()

            Dim type As String = CMD.ExecuteScalar().ToString
            TextBoxType.Text = type

            If CN.State = ConnectionState.Open Then
                CN.Close()
            End If
        End If
    End Sub

    'Warehouses DataGridView
    Private Sub DataGridView3_CellContentClick(sender As Object, e As DataGridViewCellEventArgs) Handles WarehousesDataGridView.CellClick
        Dim index As Integer = WarehousesDataGridView.CurrentRow.Index
        'To not crash when user clicks in the header
        If (index = -1) Then
        Else
            Dim selectedRow As DataGridViewRow = WarehousesDataGridView.Rows(index)
            Dim warehouseID As Integer = selectedRow.Cells(0).Value
            loadWarehousesProducts(warehouseID)
        End If
    End Sub

    'Warehouses' Products DataGridView
    Private Sub DataGridView4_CellContentClick(sender As Object, e As DataGridViewCellEventArgs) Handles WarehousesProductsDataGridView.CellClick
        Dim index As Integer = e.RowIndex
        'To not crash when user clicks in the header
        If (index = -1) Then
        Else
            Dim selectedRow As DataGridViewRow = WarehousesProductsDataGridView.Rows(index)
            Dim productName As String = selectedRow.Cells(0).Value.ToString

            TextBoxName2.Text = selectedRow.Cells(0).Value.ToString
            TextBoxPrice2.Text = selectedRow.Cells(1).Value.ToString
            TextBoxUnits2.Text = selectedRow.Cells(2).Value.ToString

            Button5.Enabled = True
            Button8.Enabled = True

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "SELECT Artigo.Codigo FROM Projeto.Artigo Where Artigo.Nome = @productName"
            CMD.Parameters.Add("@productName", SqlDbType.VarChar, 40)
            CMD.Parameters("@productName").Value = productName
            CN.Open()

            Dim code As String = CMD.ExecuteScalar().ToString
            TextBoxCode2.Text = code
            CN.Close()

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "SELECT Artigo.Categoria FROM Projeto.Artigo Where Artigo.Nome = @productName"
            CMD.Parameters.Add("@productName", SqlDbType.VarChar, 40)
            CMD.Parameters("@productName").Value = productName
            CN.Open()

            Dim type As String = CMD.ExecuteScalar().ToString
            TextBoxType2.Text = type

            If CN.State = ConnectionState.Open Then
                CN.Close()
            End If
        End If
    End Sub

    'To clear Warehouses' DataGridView when a new one Store is selected 
    Private Sub clearWarehousesProducts()
        WarehousesProductsDataGridView.DataSource = Nothing
    End Sub

    'Form Loader
    Private Sub Stores_Load(sender As Object, e As EventArgs) Handles MyBase.Load
        StoresDataGridView.ClearSelection()
        TextBoxSearch.Text = ""
        TextBoxSearch2.Text = ""
    End Sub

    'To add a new Store
    Public Sub addStore(ByVal storeName As String, ByVal storeLocation As String)
        Dim numStore As Integer = StoresDataGridView.Rows.Count + 1

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Add_newStore @StoreNum, @Name, @Location"
        CMD.Parameters.Add("@StoreNum", SqlDbType.Int)
        CMD.Parameters.Add("@Name", SqlDbType.VarChar, 30)
        CMD.Parameters.Add("@Location", SqlDbType.VarChar, 20)
        CMD.Parameters("@StoreNum").Value = numStore
        CMD.Parameters("@Name").Value = storeName
        CMD.Parameters("@Location").Value = storeLocation
        CN.Open()
        CMD.ExecuteScalar()
        loadStores()
        CN.Close()

    End Sub

    'Remove Store Button
    Private Sub Button11_Click(sender As Object, e As EventArgs) Handles Button11.Click
        Dim result As DialogResult = MessageBox.Show("Do you really want to delete the store selected?",
                                                     "Information", MessageBoxButtons.YesNo)

        Button11.Enabled = False
        Button7.Enabled = False
        Button6.Enabled = False
        Button20.Enabled = False
        Button22.Enabled = False
        Button14.Enabled = False
        Button13.Enabled = False
        Button9.Enabled = False
        Button8.Enabled = False
        Button5.Enabled = False

        If (result = DialogResult.Yes) Then
            Dim index As Integer = StoresDataGridView.CurrentRow.Index
            Dim selectedRow As DataGridViewRow = StoresDataGridView.Rows(index)
            Dim numStore As Integer = selectedRow.Cells(0).Value

            CMD = New SqlCommand()
            CMD.Connection = CN
            CMD.CommandText = "EXEC Projeto.Remove_Store @StoreNum"
            CMD.Parameters.Add("@StoreNum", SqlDbType.Int)
            CMD.Parameters("@StoreNum").Value = numStore
            CN.Open()
            CMD.ExecuteScalar()
            loadStores()
            CN.Close()

            ProductsDataGridView.DataSource = Nothing
            WarehousesDataGridView.DataSource = Nothing
            WarehousesProductsDataGridView.DataSource = Nothing
        End If
    End Sub

    'To add a Warehouse
    Public Sub addWarehouse(ByVal totalStorage As Integer)
        Dim index As Integer = StoresDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = StoresDataGridView.Rows(index)
        Dim numStore As Integer = selectedRow.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT MAX(Armazem.IDArmazem) FROM Projeto.Armazem"
        CN.Open()
        Dim WarehouseNumber As Integer = CMD.ExecuteScalar() + 10
        CN.Close()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Add_Warehouse @WarehouseID, @Storage, @StoreNum"
        CMD.Parameters.Add("@WarehouseID", SqlDbType.Int)
        CMD.Parameters.Add("@Storage", SqlDbType.Int)
        CMD.Parameters.Add("@StoreNum", SqlDbType.Int)
        CMD.Parameters("@WarehouseID").Value = WarehouseNumber
        CMD.Parameters("@Storage").Value = totalStorage
        CMD.Parameters("@StoreNum").Value = numStore
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadWarehouses(numStore)
    End Sub

    'Load Warehouses DataGridView
    Private Sub loadWarehouses(ByVal numStore As Integer)
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Number, Storage FROM Warehouse WHERE Store = @store"
        CMD.Parameters.Add("@store", SqlDbType.Int)
        CMD.Parameters("@store").Value = numStore
        CN.Open()

        Dim ds As New DataSet()

        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(ds)

        With WarehousesDataGridView
            .DataSource = ds.Tables(0)
            .Columns(0).Width = 138
            .Columns(1).Width = 138
            .ClearSelection()
        End With
        CN.Close()
    End Sub

    'Remove Warehouse Button
    Private Sub Button13_Click(sender As Object, e As EventArgs) Handles Button13.Click
        Dim index As Integer = StoresDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = StoresDataGridView.Rows(index)
        Dim numStore As Integer = selectedRow.Cells(0).Value
        Dim index2 As Integer = WarehousesDataGridView.CurrentRow.Index
        Dim selectedRow2 As DataGridViewRow = WarehousesDataGridView.Rows(index2)
        Dim warehouse As Integer = selectedRow2.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Remove_Warehouse @WarehouseID"
        CMD.Parameters.Add("@WarehouseID", SqlDbType.Int)
        CMD.Parameters("@WarehouseID").Value = warehouse
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadWarehouses(numStore)

    End Sub

    Private Sub Label1_Click(sender As Object, e As EventArgs) Handles Label1.Click
        Label1.Enabled = False
        Label1.Visible = False
    End Sub

    'Buy Products
    Public Sub BuyProduct(ByVal NIF As Integer, ByVal workersCode As Integer, ByVal units As Integer)
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT MAX(Compra.NumCompra) FROM Projeto.Compra"
        CN.Open()
        Dim purchasedID As Integer = CMD.ExecuteScalar + 1
        CN.Close()

        Dim regDate As Date = Date.Now()
        Dim todaysDate As String = regDate.ToString("yyyy-MM-dd")

        Dim index As Integer = ProductsDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = ProductsDataGridView.Rows(index)
        Dim price As Double = selectedRow.Cells(1).Value

        Dim value As Double = price * units

        Dim index2 As Integer = StoresDataGridView.CurrentRow.Index
        Dim selectedRow2 As DataGridViewRow = StoresDataGridView.Rows(index2)
        Dim numStore As Integer = selectedRow2.Cells(0).Value

        Dim productCode As Integer = TextBoxCode.Text

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.BuyProduct @PurchaseID, @Date, @Value, @NIF, @WorkersCode, @StoreNum, 
							                   @Code, @Quant"
        CMD.Parameters.Add("@PurchaseID", SqlDbType.Int)
        CMD.Parameters.Add("@Date", SqlDbType.Date)
        CMD.Parameters.Add("@Value", SqlDbType.Decimal)
        CMD.Parameters.Add("@NIF", SqlDbType.BigInt)
        CMD.Parameters.Add("@WorkersCode", SqlDbType.Int)
        CMD.Parameters.Add("@StoreNum", SqlDbType.Int)
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Quant", SqlDbType.Int)
        CMD.Parameters("@PurchaseID").Value = purchasedID
        CMD.Parameters("@Date").Value = todaysDate
        CMD.Parameters("@Value").Value = value
        CMD.Parameters("@NIF").Value = NIF
        CMD.Parameters("@WorkersCode").Value = workersCode
        CMD.Parameters("@StoreNum").Value = numStore
        CMD.Parameters("@Code").Value = productCode
        CMD.Parameters("@Quant").Value = units
        'Try
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadStoresProducts(numStore)
        'Catch SqlEx As System.Data.SqlClient.SqlException
        '    MsgBox("There's not enough units in the store!", MsgBoxStyle.Information, "ERROR")
        'End Try
    End Sub

    'Return Product
    Public Sub returnProduct(ByVal nif As Integer, ByVal workersCode As Integer, ByVal productCode As Integer, ByVal units As Integer)
        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT MAX(Devolucao.IDDevolucao) FROM Projeto.Devolucao"
        CN.Open()
        Dim returnID As Integer = CMD.ExecuteScalar + 1
        CN.Close()

        Dim regDate As Date = Date.Now()
        Dim todaysDate As String = regDate.ToString("yyyy-MM-dd")

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Artigo.Preco FROM Projeto.Artigo WHERE Artigo.Codigo = @code"
        CMD.Parameters.Add("@Code", SqlDbType.Decimal)
        CMD.Parameters("@Code").Value = productCode
        CN.Open()
        Dim price As Double = CMD.ExecuteScalar
        CN.Close()

        Dim value As Double = price * units

        Dim index As Integer = StoresDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = StoresDataGridView.Rows(index)
        Dim numStore As Integer = selectedRow.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.ReturnProduct @ReturnID, @Date, @Value, @NIF, @WorkersCode, @StoreNum, 
							                          @Code, @Quant"
        CMD.Parameters.Add("@ReturnID", SqlDbType.Int)
        CMD.Parameters.Add("@Date", SqlDbType.Date)
        CMD.Parameters.Add("@Value", SqlDbType.Decimal)
        CMD.Parameters.Add("@NIF", SqlDbType.BigInt)
        CMD.Parameters.Add("@WorkersCode", SqlDbType.Int)
        CMD.Parameters.Add("@StoreNum", SqlDbType.Int)
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Quant", SqlDbType.Int)
        CMD.Parameters("@ReturnID").Value = returnID
        CMD.Parameters("@Date").Value = todaysDate
        CMD.Parameters("@Value").Value = value
        CMD.Parameters("@NIF").Value = nif
        CMD.Parameters("@WorkersCode").Value = workersCode
        CMD.Parameters("@StoreNum").Value = numStore
        CMD.Parameters("@Code").Value = productCode
        CMD.Parameters("@Quant").Value = units
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadStoresProducts(numStore)
    End Sub

    'To add products from an warehouse to the respective store
    Public Sub warehouseToStore(ByVal units As Integer)
        Dim index As Integer = WarehousesDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = WarehousesDataGridView.Rows(index)
        Dim warehouseID As Integer = selectedRow.Cells(0).Value

        Dim code As Integer = TextBoxCode2.Text

        Dim index2 As Integer = StoresDataGridView.CurrentRow.Index
        Dim selectedRow2 As DataGridViewRow = StoresDataGridView.Rows(index2)
        Dim numStore As Integer = selectedRow2.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.ProductsFromWarehouseToStore @WarehouseID, @Code, @Store, @Quant"
        CMD.Parameters.Add("@WarehouseID", SqlDbType.Int)
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Store", SqlDbType.Int)
        CMD.Parameters.Add("@Quant", SqlDbType.Int)
        CMD.Parameters("@WarehouseID").Value = warehouseID
        CMD.Parameters("@Code").Value = code
        CMD.Parameters("@Store").Value = numStore
        CMD.Parameters("@Quant").Value = units
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadStoresProducts(numStore)
        loadWarehousesProducts(warehouseID)
    End Sub

    'Load Store's Products DataGridView
    Private Sub loadStoresProducts(ByVal numStore As Integer)
        Dim lastIndex As Integer = -1
        Dim index As Integer = StoresDataGridView.CurrentRow.Index

        If (lastIndex <> index) Then
            clearWarehousesProducts()
            TextBoxName.Text = ""
            TextBoxPrice.Text = ""
            TextBoxUnits.Text = ""
            TextBoxCode.Text = ""
            TextBoxType.Text = ""
            TextBoxName2.Text = ""
            TextBoxPrice2.Text = ""
            TextBoxUnits2.Text = ""
            TextBoxCode2.Text = ""
            TextBoxType2.Text = ""
            TextBoxTotalStorage.Text = ""
            TextBoxStorageOccupied.Text = ""
            lastIndex = index
        End If

        Button7.Enabled = True
        Button11.Enabled = True
        Button14.Enabled = True
        Button22.Enabled = True
        TextBoxSearch.Enabled = True
        Label1.Enabled = True

        Dim ds As New DataSet()

        CMD = New SqlCommand
        CMD.Connection = CN
        CMD.CommandText = "SELECT Name, Price, Units FROM StoreProducts WHERE Store = @store"
        CMD.Parameters.Add("@store", SqlDbType.VarChar, 1)
        CMD.Parameters("@store").Value = numStore
        CN.Open()

        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(ds)

        With ProductsDataGridView
            .DataSource = ds.Tables(0)
            Dim scroll As VScrollBar = ProductsDataGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
            If (scroll.Visible) Then
                .Columns(0).Width = (ProductsDataGridView.Size.Width - 20) * 0.69
                .Columns(1).Width = (ProductsDataGridView.Size.Width - 20) * 0.16
                .Columns(2).Width = (ProductsDataGridView.Size.Width - 20) * 0.15
            Else
                .Columns(0).Width = (ProductsDataGridView.Size.Width - 3) * 0.69
                .Columns(1).Width = (ProductsDataGridView.Size.Width - 3) * 0.16
                .Columns(2).Width = (ProductsDataGridView.Size.Width - 3) * 0.15
            End If
            .ClearSelection()
        End With
        CN.Close()
    End Sub

    'Load Warehouse's Products DataGridView
    Private Sub loadWarehousesProducts(ByVal warehouseID As Integer)
        Dim lastIndex As Integer = -1
        Dim index As Integer = WarehousesDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = WarehousesDataGridView.Rows(index)

        If (lastIndex <> index) Then
            TextBoxName2.Text = ""
            TextBoxPrice2.Text = ""
            TextBoxUnits2.Text = ""
            TextBoxCode2.Text = ""
            TextBoxType2.Text = ""
            lastIndex = index
        End If

        Button9.Enabled = True
        Button13.Enabled = True
        TextBoxSearch2.Enabled = True

        Dim ds As New DataSet()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "SELECT Name, Price, Units FROM WarehousesProducts 
                           		        WHERE WarehouseID = @warehouseID"
        CMD.Parameters.Add("@warehouseID", SqlDbType.VarChar, 3)
        CMD.Parameters("@warehouseID").Value = warehouseID
        CN.Open()

        Dim adapter As New SqlDataAdapter(CMD)
        adapter.Fill(ds)

        With WarehousesProductsDataGridView
            .DataSource = ds.Tables(0)
            Dim scroll As VScrollBar = WarehousesProductsDataGridView.Controls.OfType(Of VScrollBar).SingleOrDefault
            If (scroll.Visible) Then
                .Columns(0).Width = (WarehousesProductsDataGridView.Size.Width - 20) * 0.69
                .Columns(1).Width = (WarehousesProductsDataGridView.Size.Width - 20) * 0.16
                .Columns(2).Width = (WarehousesProductsDataGridView.Size.Width - 20) * 0.15
            Else
                .Columns(0).Width = (WarehousesProductsDataGridView.Size.Width - 3) * 0.69
                .Columns(1).Width = (WarehousesProductsDataGridView.Size.Width - 3) * 0.16
                .Columns(2).Width = (WarehousesProductsDataGridView.Size.Width - 3) * 0.15
            End If
            .ClearSelection()
        End With
        CN.Close()

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "Select Sum(Artigo_Armazem.QuantArtigos) AS Storage_Occupied
                           From Projeto.Artigo_Armazem
                           Where Artigo_Armazem.IDArmazem = @warehouseID"
        CMD.Parameters.Add("@warehouseID", SqlDbType.VarChar, 3)
        CMD.Parameters("@warehouseID").Value = warehouseID
        CN.Open()

        Dim storageOccupied As String

        If (CMD.ExecuteScalar().ToString.Equals("")) Then
            storageOccupied = "0"
        Else
            storageOccupied = CMD.ExecuteScalar().ToString
        End If
        TextBoxTotalStorage.Text = selectedRow.Cells(1).Value.ToString
        TextBoxStorageOccupied.Text = storageOccupied

        If CN.State = ConnectionState.Open Then
            CN.Close()
        End If
    End Sub

    'To add a product to the selected store
    Public Sub addProduct(ByVal name As String, ByVal price As Double, ByVal code As Integer, ByVal type As String, ByVal units As Integer)
        Dim index As Integer = StoresDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = StoresDataGridView.Rows(index)
        Dim numStore As Integer = selectedRow.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Add_storeProduct @Code, @Price, @Name, @Type, @Store, @Units"
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Price", SqlDbType.Decimal)
        CMD.Parameters.Add("@Name", SqlDbType.VarChar, 40)
        CMD.Parameters.Add("@Type", SqlDbType.VarChar, 20)
        CMD.Parameters.Add("@Store", SqlDbType.Int)
        CMD.Parameters.Add("@Units", SqlDbType.Int)
        CMD.Parameters("@Code").Value = code
        CMD.Parameters("@Price").Value = price
        CMD.Parameters("@Name").Value = name
        CMD.Parameters("@Type").Value = type
        CMD.Parameters("@Store").Value = numStore
        CMD.Parameters("@Units").Value = units
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadStoresProducts(numStore)
    End Sub

    'To remove a Store Product
    Public Sub removeProduct(ByVal units As Integer)
        Dim index As Integer = StoresDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = StoresDataGridView.Rows(index)
        Dim numStore As Integer = selectedRow.Cells(0).Value
        Dim code As Integer = TextBoxCode.Text

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Remove_storeProduct @Store, @Code, @Units"
        CMD.Parameters.Add("@Store", SqlDbType.Int)
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Units", SqlDbType.Int)
        CMD.Parameters("@Store").Value = numStore
        CMD.Parameters("@Code").Value = code
        CMD.Parameters("@Units").Value = units
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadStoresProducts(numStore)
    End Sub

    'To add a product to the selected warehouse
    Public Sub addWarehouseProduct(ByVal name As String, ByVal price As Double, ByVal code As Integer, ByVal type As String, ByVal units As Integer)
        Dim index As Integer = WarehousesDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = WarehousesDataGridView.Rows(index)
        Dim warehouseID As Integer = selectedRow.Cells(0).Value

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Add_warehouseProduct @Code, @Price, @Name, @Type, @Warehouse, @Units"
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Price", SqlDbType.Decimal)
        CMD.Parameters.Add("@Name", SqlDbType.VarChar, 40)
        CMD.Parameters.Add("@Type", SqlDbType.VarChar, 20)
        CMD.Parameters.Add("@Warehouse", SqlDbType.Int)
        CMD.Parameters.Add("@Units", SqlDbType.Int)
        CMD.Parameters("@Code").Value = code
        CMD.Parameters("@Price").Value = price
        CMD.Parameters("@Name").Value = name
        CMD.Parameters("@Type").Value = type
        CMD.Parameters("@Warehouse").Value = warehouseID
        CMD.Parameters("@Units").Value = units
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadWarehousesProducts(warehouseID)
    End Sub

    'To remove a Warehouse Product
    Public Sub removeWarehouseProduct(ByVal units As Integer)
        Dim index As Integer = WarehousesDataGridView.CurrentRow.Index
        Dim selectedRow As DataGridViewRow = WarehousesDataGridView.Rows(index)
        Dim warehouseID As Integer = selectedRow.Cells(0).Value

        Dim code As Integer = TextBoxCode2.Text

        CMD = New SqlCommand()
        CMD.Connection = CN
        CMD.CommandText = "EXEC Projeto.Remove_WarehouseProduct @Warehouse, @Code, @Units"
        CMD.Parameters.Add("@Warehouse", SqlDbType.Int)
        CMD.Parameters.Add("@Code", SqlDbType.Int)
        CMD.Parameters.Add("@Units", SqlDbType.Int)
        CMD.Parameters("@Warehouse").Value = warehouseID
        CMD.Parameters("@Code").Value = code
        CMD.Parameters("@Units").Value = units
        CN.Open()
        CMD.ExecuteScalar()
        CN.Close()
        loadWarehousesProducts(warehouseID)
    End Sub
End Class