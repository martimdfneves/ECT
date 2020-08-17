namespace Motoshop
{
    partial class Login
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btn_salesman = new System.Windows.Forms.Button();
            this.btn_mechanic = new System.Windows.Forms.Button();
            this.staff_list = new System.Windows.Forms.DataGridView();
            this.btn_login = new System.Windows.Forms.Button();
            this.btn_back = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.staff_list)).BeginInit();
            this.SuspendLayout();
            // 
            // btn_salesman
            // 
            this.btn_salesman.Location = new System.Drawing.Point(71, 118);
            this.btn_salesman.Name = "btn_salesman";
            this.btn_salesman.Size = new System.Drawing.Size(124, 75);
            this.btn_salesman.TabIndex = 0;
            this.btn_salesman.Text = "Salesman";
            this.btn_salesman.UseVisualStyleBackColor = true;
            this.btn_salesman.Click += new System.EventHandler(this.btn_salesman_Click);
            // 
            // btn_mechanic
            // 
            this.btn_mechanic.Location = new System.Drawing.Point(219, 118);
            this.btn_mechanic.Name = "btn_mechanic";
            this.btn_mechanic.Size = new System.Drawing.Size(124, 75);
            this.btn_mechanic.TabIndex = 1;
            this.btn_mechanic.Text = "Mechanic";
            this.btn_mechanic.UseVisualStyleBackColor = true;
            this.btn_mechanic.Click += new System.EventHandler(this.btn_mechanic_Click);
            // 
            // staff_list
            // 
            this.staff_list.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.staff_list.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.staff_list.EditMode = System.Windows.Forms.DataGridViewEditMode.EditOnEnter;
            this.staff_list.Location = new System.Drawing.Point(71, 12);
            this.staff_list.Name = "staff_list";
            this.staff_list.ReadOnly = true;
            this.staff_list.Size = new System.Drawing.Size(272, 270);
            this.staff_list.TabIndex = 2;
            this.staff_list.Visible = false;
            // 
            // btn_login
            // 
            this.btn_login.Location = new System.Drawing.Point(162, 288);
            this.btn_login.Name = "btn_login";
            this.btn_login.Size = new System.Drawing.Size(181, 23);
            this.btn_login.TabIndex = 3;
            this.btn_login.Text = "Login";
            this.btn_login.UseVisualStyleBackColor = true;
            this.btn_login.Visible = false;
            this.btn_login.Click += new System.EventHandler(this.btn_login_Click);
            // 
            // btn_back
            // 
            this.btn_back.Location = new System.Drawing.Point(71, 288);
            this.btn_back.Name = "btn_back";
            this.btn_back.Size = new System.Drawing.Size(85, 23);
            this.btn_back.TabIndex = 4;
            this.btn_back.Text = "Back";
            this.btn_back.UseVisualStyleBackColor = true;
            this.btn_back.Visible = false;
            this.btn_back.Click += new System.EventHandler(this.btn_back_Click);
            // 
            // Login
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(418, 326);
            this.Controls.Add(this.btn_back);
            this.Controls.Add(this.btn_login);
            this.Controls.Add(this.staff_list);
            this.Controls.Add(this.btn_mechanic);
            this.Controls.Add(this.btn_salesman);
            this.Name = "Login";
            this.Text = "Login";
            this.Load += new System.EventHandler(this.Login_Load);
            ((System.ComponentModel.ISupportInitialize)(this.staff_list)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btn_salesman;
        private System.Windows.Forms.Button btn_mechanic;
        private System.Windows.Forms.Button btn_login;
        private System.Windows.Forms.DataGridView staff_list;
        private System.Windows.Forms.Button btn_back;
    }
}