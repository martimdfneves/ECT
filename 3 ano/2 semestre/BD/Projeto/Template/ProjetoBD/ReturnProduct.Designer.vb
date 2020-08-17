<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Partial Class ReturnProduct
    Inherits System.Windows.Forms.Form

    'Form overrides dispose to clean up the component list.
    <System.Diagnostics.DebuggerNonUserCode()> _
    Protected Overrides Sub Dispose(ByVal disposing As Boolean)
        Try
            If disposing AndAlso components IsNot Nothing Then
                components.Dispose()
            End If
        Finally
            MyBase.Dispose(disposing)
        End Try
    End Sub

    'Required by the Windows Form Designer
    Private components As System.ComponentModel.IContainer

    'NOTE: The following procedure is required by the Windows Form Designer
    'It can be modified using the Windows Form Designer.  
    'Do not modify it using the code editor.
    <System.Diagnostics.DebuggerStepThrough()> _
    Private Sub InitializeComponent()
        Me.Button2 = New System.Windows.Forms.Button()
        Me.Button1 = New System.Windows.Forms.Button()
        Me.UnitsTextBox = New System.Windows.Forms.TextBox()
        Me.Label9 = New System.Windows.Forms.Label()
        Me.WorkersNameTextBox = New System.Windows.Forms.TextBox()
        Me.Label4 = New System.Windows.Forms.Label()
        Me.Label5 = New System.Windows.Forms.Label()
        Me.ClientsNameTextBox = New System.Windows.Forms.TextBox()
        Me.Label3 = New System.Windows.Forms.Label()
        Me.NIFTextBox = New System.Windows.Forms.TextBox()
        Me.Label2 = New System.Windows.Forms.Label()
        Me.Label1 = New System.Windows.Forms.Label()
        Me.ProductTextBox = New System.Windows.Forms.TextBox()
        Me.Label6 = New System.Windows.Forms.Label()
        Me.ProductsNameTextBox = New System.Windows.Forms.TextBox()
        Me.Label7 = New System.Windows.Forms.Label()
        Me.CodeComboBox = New System.Windows.Forms.ComboBox()
        Me.SuspendLayout()
        '
        'Button2
        '
        Me.Button2.Location = New System.Drawing.Point(181, 193)
        Me.Button2.Name = "Button2"
        Me.Button2.Size = New System.Drawing.Size(75, 23)
        Me.Button2.TabIndex = 79
        Me.Button2.Text = "Cancel"
        Me.Button2.UseVisualStyleBackColor = True
        '
        'Button1
        '
        Me.Button1.Location = New System.Drawing.Point(91, 193)
        Me.Button1.Name = "Button1"
        Me.Button1.Size = New System.Drawing.Size(75, 23)
        Me.Button1.TabIndex = 78
        Me.Button1.Text = "Confirm"
        Me.Button1.UseVisualStyleBackColor = True
        '
        'UnitsTextBox
        '
        Me.UnitsTextBox.Location = New System.Drawing.Point(11, 195)
        Me.UnitsTextBox.Name = "UnitsTextBox"
        Me.UnitsTextBox.Size = New System.Drawing.Size(57, 20)
        Me.UnitsTextBox.TabIndex = 76
        '
        'Label9
        '
        Me.Label9.AutoSize = True
        Me.Label9.Location = New System.Drawing.Point(8, 179)
        Me.Label9.Name = "Label9"
        Me.Label9.Size = New System.Drawing.Size(46, 13)
        Me.Label9.TabIndex = 77
        Me.Label9.Text = "Nº Units"
        '
        'WorkersNameTextBox
        '
        Me.WorkersNameTextBox.Enabled = False
        Me.WorkersNameTextBox.Location = New System.Drawing.Point(130, 105)
        Me.WorkersNameTextBox.Name = "WorkersNameTextBox"
        Me.WorkersNameTextBox.Size = New System.Drawing.Size(126, 20)
        Me.WorkersNameTextBox.TabIndex = 75
        '
        'Label4
        '
        Me.Label4.AutoSize = True
        Me.Label4.Location = New System.Drawing.Point(127, 89)
        Me.Label4.Name = "Label4"
        Me.Label4.Size = New System.Drawing.Size(80, 13)
        Me.Label4.TabIndex = 74
        Me.Label4.Text = "Worker's Name"
        '
        'Label5
        '
        Me.Label5.AutoSize = True
        Me.Label5.Location = New System.Drawing.Point(8, 89)
        Me.Label5.Name = "Label5"
        Me.Label5.Size = New System.Drawing.Size(77, 13)
        Me.Label5.TabIndex = 72
        Me.Label5.Text = "Worker's Code"
        '
        'ClientsNameTextBox
        '
        Me.ClientsNameTextBox.Enabled = False
        Me.ClientsNameTextBox.Location = New System.Drawing.Point(130, 60)
        Me.ClientsNameTextBox.Name = "ClientsNameTextBox"
        Me.ClientsNameTextBox.Size = New System.Drawing.Size(126, 20)
        Me.ClientsNameTextBox.TabIndex = 71
        '
        'Label3
        '
        Me.Label3.AutoSize = True
        Me.Label3.Location = New System.Drawing.Point(127, 44)
        Me.Label3.Name = "Label3"
        Me.Label3.Size = New System.Drawing.Size(71, 13)
        Me.Label3.TabIndex = 70
        Me.Label3.Text = "Client's Name"
        '
        'NIFTextBox
        '
        Me.NIFTextBox.Location = New System.Drawing.Point(11, 60)
        Me.NIFTextBox.Name = "NIFTextBox"
        Me.NIFTextBox.Size = New System.Drawing.Size(100, 20)
        Me.NIFTextBox.TabIndex = 69
        '
        'Label2
        '
        Me.Label2.AutoSize = True
        Me.Label2.Location = New System.Drawing.Point(8, 44)
        Me.Label2.Name = "Label2"
        Me.Label2.Size = New System.Drawing.Size(60, 13)
        Me.Label2.TabIndex = 68
        Me.Label2.Text = "Client's NIF"
        Me.Label2.UseWaitCursor = True
        '
        'Label1
        '
        Me.Label1.AutoSize = True
        Me.Label1.Font = New System.Drawing.Font("Segoe UI", 15.75!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.Label1.Location = New System.Drawing.Point(54, 3)
        Me.Label1.Name = "Label1"
        Me.Label1.Size = New System.Drawing.Size(163, 30)
        Me.Label1.TabIndex = 67
        Me.Label1.Text = "Return Product"
        '
        'ProductTextBox
        '
        Me.ProductTextBox.Location = New System.Drawing.Point(12, 152)
        Me.ProductTextBox.Name = "ProductTextBox"
        Me.ProductTextBox.Size = New System.Drawing.Size(100, 20)
        Me.ProductTextBox.TabIndex = 81
        '
        'Label6
        '
        Me.Label6.AutoSize = True
        Me.Label6.Location = New System.Drawing.Point(9, 136)
        Me.Label6.Name = "Label6"
        Me.Label6.Size = New System.Drawing.Size(79, 13)
        Me.Label6.TabIndex = 80
        Me.Label6.Text = "Product's Code"
        '
        'ProductsNameTextBox
        '
        Me.ProductsNameTextBox.Enabled = False
        Me.ProductsNameTextBox.Location = New System.Drawing.Point(130, 152)
        Me.ProductsNameTextBox.Name = "ProductsNameTextBox"
        Me.ProductsNameTextBox.Size = New System.Drawing.Size(126, 20)
        Me.ProductsNameTextBox.TabIndex = 83
        '
        'Label7
        '
        Me.Label7.AutoSize = True
        Me.Label7.Location = New System.Drawing.Point(127, 136)
        Me.Label7.Name = "Label7"
        Me.Label7.Size = New System.Drawing.Size(82, 13)
        Me.Label7.TabIndex = 82
        Me.Label7.Text = "Product's Name"
        '
        'CodeComboBox
        '
        Me.CodeComboBox.FormattingEnabled = True
        Me.CodeComboBox.Location = New System.Drawing.Point(11, 104)
        Me.CodeComboBox.Name = "CodeComboBox"
        Me.CodeComboBox.Size = New System.Drawing.Size(101, 21)
        Me.CodeComboBox.TabIndex = 84
        '
        'ReturnProduct
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(267, 224)
        Me.Controls.Add(Me.CodeComboBox)
        Me.Controls.Add(Me.ProductsNameTextBox)
        Me.Controls.Add(Me.Label7)
        Me.Controls.Add(Me.ProductTextBox)
        Me.Controls.Add(Me.Label6)
        Me.Controls.Add(Me.Button2)
        Me.Controls.Add(Me.Button1)
        Me.Controls.Add(Me.UnitsTextBox)
        Me.Controls.Add(Me.Label9)
        Me.Controls.Add(Me.WorkersNameTextBox)
        Me.Controls.Add(Me.Label4)
        Me.Controls.Add(Me.Label5)
        Me.Controls.Add(Me.ClientsNameTextBox)
        Me.Controls.Add(Me.Label3)
        Me.Controls.Add(Me.NIFTextBox)
        Me.Controls.Add(Me.Label2)
        Me.Controls.Add(Me.Label1)
        Me.Name = "ReturnProduct"
        Me.Text = "ReturnProduct"
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub

    Friend WithEvents Button2 As Button
    Friend WithEvents Button1 As Button
    Friend WithEvents UnitsTextBox As TextBox
    Friend WithEvents Label9 As Label
    Friend WithEvents WorkersNameTextBox As TextBox
    Friend WithEvents Label4 As Label
    Friend WithEvents Label5 As Label
    Friend WithEvents ClientsNameTextBox As TextBox
    Friend WithEvents Label3 As Label
    Friend WithEvents NIFTextBox As TextBox
    Friend WithEvents Label2 As Label
    Friend WithEvents Label1 As Label
    Friend WithEvents ProductTextBox As TextBox
    Friend WithEvents Label6 As Label
    Friend WithEvents ProductsNameTextBox As TextBox
    Friend WithEvents Label7 As Label
    Friend WithEvents CodeComboBox As ComboBox
End Class
