<Global.Microsoft.VisualBasic.CompilerServices.DesignerGenerated()> _
Partial Class BuyProduct
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
        Me.Label1 = New System.Windows.Forms.Label()
        Me.Label2 = New System.Windows.Forms.Label()
        Me.NIFTextBox = New System.Windows.Forms.TextBox()
        Me.Label3 = New System.Windows.Forms.Label()
        Me.ClientsNameTextBox = New System.Windows.Forms.TextBox()
        Me.WorkersNameTextBox = New System.Windows.Forms.TextBox()
        Me.Label4 = New System.Windows.Forms.Label()
        Me.Label5 = New System.Windows.Forms.Label()
        Me.UnitsTextBox = New System.Windows.Forms.TextBox()
        Me.Label9 = New System.Windows.Forms.Label()
        Me.Button2 = New System.Windows.Forms.Button()
        Me.Button1 = New System.Windows.Forms.Button()
        Me.CodeComboBox = New System.Windows.Forms.ComboBox()
        Me.SuspendLayout()
        '
        'Label1
        '
        Me.Label1.AutoSize = True
        Me.Label1.Font = New System.Drawing.Font("Segoe UI", 15.75!, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, CType(0, Byte))
        Me.Label1.Location = New System.Drawing.Point(73, 5)
        Me.Label1.Name = "Label1"
        Me.Label1.Size = New System.Drawing.Size(134, 30)
        Me.Label1.TabIndex = 0
        Me.Label1.Text = "Buy Product"
        '
        'Label2
        '
        Me.Label2.AutoSize = True
        Me.Label2.Location = New System.Drawing.Point(10, 46)
        Me.Label2.Name = "Label2"
        Me.Label2.Size = New System.Drawing.Size(60, 13)
        Me.Label2.TabIndex = 1
        Me.Label2.Text = "Client's NIF"
        Me.Label2.UseWaitCursor = True
        '
        'NIFTextBox
        '
        Me.NIFTextBox.Location = New System.Drawing.Point(13, 62)
        Me.NIFTextBox.Name = "NIFTextBox"
        Me.NIFTextBox.Size = New System.Drawing.Size(100, 20)
        Me.NIFTextBox.TabIndex = 2
        '
        'Label3
        '
        Me.Label3.AutoSize = True
        Me.Label3.Location = New System.Drawing.Point(129, 46)
        Me.Label3.Name = "Label3"
        Me.Label3.Size = New System.Drawing.Size(71, 13)
        Me.Label3.TabIndex = 3
        Me.Label3.Text = "Client's Name"
        '
        'ClientsNameTextBox
        '
        Me.ClientsNameTextBox.Enabled = False
        Me.ClientsNameTextBox.Location = New System.Drawing.Point(132, 62)
        Me.ClientsNameTextBox.Name = "ClientsNameTextBox"
        Me.ClientsNameTextBox.Size = New System.Drawing.Size(126, 20)
        Me.ClientsNameTextBox.TabIndex = 4
        '
        'WorkersNameTextBox
        '
        Me.WorkersNameTextBox.Enabled = False
        Me.WorkersNameTextBox.Location = New System.Drawing.Point(132, 107)
        Me.WorkersNameTextBox.Name = "WorkersNameTextBox"
        Me.WorkersNameTextBox.Size = New System.Drawing.Size(126, 20)
        Me.WorkersNameTextBox.TabIndex = 8
        '
        'Label4
        '
        Me.Label4.AutoSize = True
        Me.Label4.Location = New System.Drawing.Point(129, 91)
        Me.Label4.Name = "Label4"
        Me.Label4.Size = New System.Drawing.Size(80, 13)
        Me.Label4.TabIndex = 7
        Me.Label4.Text = "Worker's Name"
        '
        'Label5
        '
        Me.Label5.AutoSize = True
        Me.Label5.Location = New System.Drawing.Point(10, 91)
        Me.Label5.Name = "Label5"
        Me.Label5.Size = New System.Drawing.Size(77, 13)
        Me.Label5.TabIndex = 5
        Me.Label5.Text = "Worker's Code"
        '
        'UnitsTextBox
        '
        Me.UnitsTextBox.Location = New System.Drawing.Point(13, 153)
        Me.UnitsTextBox.Name = "UnitsTextBox"
        Me.UnitsTextBox.Size = New System.Drawing.Size(57, 20)
        Me.UnitsTextBox.TabIndex = 63
        '
        'Label9
        '
        Me.Label9.AutoSize = True
        Me.Label9.Location = New System.Drawing.Point(10, 137)
        Me.Label9.Name = "Label9"
        Me.Label9.Size = New System.Drawing.Size(46, 13)
        Me.Label9.TabIndex = 64
        Me.Label9.Text = "Nº Units"
        '
        'Button2
        '
        Me.Button2.Location = New System.Drawing.Point(183, 150)
        Me.Button2.Name = "Button2"
        Me.Button2.Size = New System.Drawing.Size(75, 23)
        Me.Button2.TabIndex = 66
        Me.Button2.Text = "Cancel"
        Me.Button2.UseVisualStyleBackColor = True
        '
        'Button1
        '
        Me.Button1.Location = New System.Drawing.Point(97, 150)
        Me.Button1.Name = "Button1"
        Me.Button1.Size = New System.Drawing.Size(75, 23)
        Me.Button1.TabIndex = 65
        Me.Button1.Text = "Confirm"
        Me.Button1.UseVisualStyleBackColor = True
        '
        'CodeComboBox
        '
        Me.CodeComboBox.FormattingEnabled = True
        Me.CodeComboBox.Location = New System.Drawing.Point(13, 106)
        Me.CodeComboBox.Name = "CodeComboBox"
        Me.CodeComboBox.Size = New System.Drawing.Size(100, 21)
        Me.CodeComboBox.TabIndex = 67
        '
        'BuyProduct
        '
        Me.AutoScaleDimensions = New System.Drawing.SizeF(6.0!, 13.0!)
        Me.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font
        Me.ClientSize = New System.Drawing.Size(270, 186)
        Me.Controls.Add(Me.CodeComboBox)
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
        Me.Name = "BuyProduct"
        Me.Text = "BuyProduct"
        Me.ResumeLayout(False)
        Me.PerformLayout()

    End Sub

    Friend WithEvents Label1 As Label
    Friend WithEvents Label2 As Label
    Friend WithEvents NIFTextBox As TextBox
    Friend WithEvents Label3 As Label
    Friend WithEvents ClientsNameTextBox As TextBox
    Friend WithEvents WorkersNameTextBox As TextBox
    Friend WithEvents Label4 As Label
    Friend WithEvents Label5 As Label
    Friend WithEvents UnitsTextBox As TextBox
    Friend WithEvents Label9 As Label
    Friend WithEvents Button2 As Button
    Friend WithEvents Button1 As Button
    Friend WithEvents CodeComboBox As ComboBox
End Class
