namespace Motoshop
{
    partial class Clients
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
            this.tb_nif = new System.Windows.Forms.TextBox();
            this.tb_name = new System.Windows.Forms.TextBox();
            this.tb_addr = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label3 = new System.Windows.Forms.Label();
            this.label4 = new System.Windows.Forms.Label();
            this.add = new System.Windows.Forms.Button();
            this.rem = new System.Windows.Forms.Button();
            this.client_grid_view = new System.Windows.Forms.DataGridView();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.client_motorcycles_grid = new System.Windows.Forms.DataGridView();
            this.client_revisions_grid = new System.Windows.Forms.DataGridView();
            this.client_rents_grid = new System.Windows.Forms.DataGridView();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.label7 = new System.Windows.Forms.Label();
            this.changed_parts_grid = new System.Windows.Forms.DataGridView();
            this.label8 = new System.Windows.Forms.Label();
            ((System.ComponentModel.ISupportInitialize)(this.client_grid_view)).BeginInit();
            this.groupBox1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.client_motorcycles_grid)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.client_revisions_grid)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.client_rents_grid)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.changed_parts_grid)).BeginInit();
            this.SuspendLayout();
            // 
            // tb_nif
            // 
            this.tb_nif.Location = new System.Drawing.Point(21, 45);
            this.tb_nif.Name = "tb_nif";
            this.tb_nif.Size = new System.Drawing.Size(192, 20);
            this.tb_nif.TabIndex = 1;
            // 
            // tb_name
            // 
            this.tb_name.Location = new System.Drawing.Point(20, 88);
            this.tb_name.Name = "tb_name";
            this.tb_name.Size = new System.Drawing.Size(294, 20);
            this.tb_name.TabIndex = 2;
            // 
            // tb_addr
            // 
            this.tb_addr.Location = new System.Drawing.Point(19, 134);
            this.tb_addr.Name = "tb_addr";
            this.tb_addr.Size = new System.Drawing.Size(295, 20);
            this.tb_addr.TabIndex = 3;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(17, 29);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(24, 13);
            this.label1.TabIndex = 4;
            this.label1.Text = "NIF";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(18, 72);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(35, 13);
            this.label2.TabIndex = 5;
            this.label2.Text = "Name";
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(18, 118);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(45, 13);
            this.label3.TabIndex = 6;
            this.label3.Text = "Address";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(896, 12);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(174, 25);
            this.label4.TabIndex = 7;
            this.label4.Text = "Insert New Client";
            // 
            // add
            // 
            this.add.Location = new System.Drawing.Point(901, 246);
            this.add.Name = "add";
            this.add.Size = new System.Drawing.Size(336, 32);
            this.add.TabIndex = 8;
            this.add.Text = "Insert";
            this.add.UseVisualStyleBackColor = true;
            this.add.Click += new System.EventHandler(this.add_Click);
            // 
            // rem
            // 
            this.rem.Location = new System.Drawing.Point(12, 468);
            this.rem.Name = "rem";
            this.rem.Size = new System.Drawing.Size(389, 39);
            this.rem.TabIndex = 9;
            this.rem.Text = "Remove";
            this.rem.UseVisualStyleBackColor = true;
            this.rem.Click += new System.EventHandler(this.rem_Click);
            // 
            // client_grid_view
            // 
            this.client_grid_view.AllowUserToAddRows = false;
            this.client_grid_view.AllowUserToDeleteRows = false;
            this.client_grid_view.AllowUserToResizeRows = false;
            this.client_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.client_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.client_grid_view.Location = new System.Drawing.Point(12, 51);
            this.client_grid_view.Name = "client_grid_view";
            this.client_grid_view.ReadOnly = true;
            this.client_grid_view.RowHeadersVisible = false;
            this.client_grid_view.Size = new System.Drawing.Size(389, 409);
            this.client_grid_view.TabIndex = 10;
            this.client_grid_view.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.client_grid_view_CellClick);
            this.client_grid_view.CellContentClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.client_grid_view_CellContentClick);
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.tb_nif);
            this.groupBox1.Controls.Add(this.tb_name);
            this.groupBox1.Controls.Add(this.tb_addr);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.label2);
            this.groupBox1.Controls.Add(this.label3);
            this.groupBox1.Location = new System.Drawing.Point(901, 51);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(336, 190);
            this.groupBox1.TabIndex = 11;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Client";
            // 
            // client_motorcycles_grid
            // 
            this.client_motorcycles_grid.AllowUserToAddRows = false;
            this.client_motorcycles_grid.AllowUserToDeleteRows = false;
            this.client_motorcycles_grid.AllowUserToResizeRows = false;
            this.client_motorcycles_grid.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.client_motorcycles_grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.client_motorcycles_grid.Location = new System.Drawing.Point(453, 51);
            this.client_motorcycles_grid.Name = "client_motorcycles_grid";
            this.client_motorcycles_grid.RowHeadersVisible = false;
            this.client_motorcycles_grid.Size = new System.Drawing.Size(395, 113);
            this.client_motorcycles_grid.TabIndex = 12;
            this.client_motorcycles_grid.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.client_motorcycles_grid_CellClick);
            // 
            // client_revisions_grid
            // 
            this.client_revisions_grid.AllowUserToAddRows = false;
            this.client_revisions_grid.AllowUserToDeleteRows = false;
            this.client_revisions_grid.AllowUserToResizeRows = false;
            this.client_revisions_grid.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.client_revisions_grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.client_revisions_grid.Location = new System.Drawing.Point(453, 223);
            this.client_revisions_grid.Name = "client_revisions_grid";
            this.client_revisions_grid.RowHeadersVisible = false;
            this.client_revisions_grid.Size = new System.Drawing.Size(273, 113);
            this.client_revisions_grid.TabIndex = 13;
            this.client_revisions_grid.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.client_revisions_grid_CellClick);
            // 
            // client_rents_grid
            // 
            this.client_rents_grid.AllowUserToAddRows = false;
            this.client_rents_grid.AllowUserToDeleteRows = false;
            this.client_rents_grid.AllowUserToResizeRows = false;
            this.client_rents_grid.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.client_rents_grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.client_rents_grid.Location = new System.Drawing.Point(453, 394);
            this.client_rents_grid.Name = "client_rents_grid";
            this.client_rents_grid.RowHeadersVisible = false;
            this.client_rents_grid.Size = new System.Drawing.Size(395, 113);
            this.client_rents_grid.TabIndex = 14;
            this.client_rents_grid.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.client_rents_grid_CellClick);
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(448, 13);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(128, 25);
            this.label5.TabIndex = 16;
            this.label5.Text = "Motorcycles";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(448, 187);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(106, 25);
            this.label6.TabIndex = 17;
            this.label6.Text = "Revisions";
            // 
            // label7
            // 
            this.label7.AutoSize = true;
            this.label7.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label7.Location = new System.Drawing.Point(448, 358);
            this.label7.Name = "label7";
            this.label7.Size = new System.Drawing.Size(68, 25);
            this.label7.TabIndex = 18;
            this.label7.Text = "Rents";
            // 
            // changed_parts_grid
            // 
            this.changed_parts_grid.AllowUserToAddRows = false;
            this.changed_parts_grid.AllowUserToDeleteRows = false;
            this.changed_parts_grid.AllowUserToResizeRows = false;
            this.changed_parts_grid.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.changed_parts_grid.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.changed_parts_grid.Location = new System.Drawing.Point(732, 223);
            this.changed_parts_grid.Name = "changed_parts_grid";
            this.changed_parts_grid.RowHeadersVisible = false;
            this.changed_parts_grid.Size = new System.Drawing.Size(116, 113);
            this.changed_parts_grid.TabIndex = 19;
            // 
            // label8
            // 
            this.label8.AutoSize = true;
            this.label8.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label8.Location = new System.Drawing.Point(7, 12);
            this.label8.Name = "label8";
            this.label8.Size = new System.Drawing.Size(78, 25);
            this.label8.TabIndex = 20;
            this.label8.Text = "Clients";
            // 
            // Clients
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1264, 681);
            this.Controls.Add(this.label8);
            this.Controls.Add(this.changed_parts_grid);
            this.Controls.Add(this.label7);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.client_rents_grid);
            this.Controls.Add(this.client_revisions_grid);
            this.Controls.Add(this.client_motorcycles_grid);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.client_grid_view);
            this.Controls.Add(this.rem);
            this.Controls.Add(this.add);
            this.Controls.Add(this.label4);
            this.Name = "Clients";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Clients_Load);
            ((System.ComponentModel.ISupportInitialize)(this.client_grid_view)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.client_motorcycles_grid)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.client_revisions_grid)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.client_rents_grid)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.changed_parts_grid)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private System.Windows.Forms.TextBox tb_nif;
        private System.Windows.Forms.TextBox tb_name;
        private System.Windows.Forms.TextBox tb_addr;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button add;
        private System.Windows.Forms.Button rem;
        private System.Windows.Forms.DataGridView client_grid_view;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.DataGridView client_motorcycles_grid;
        private System.Windows.Forms.DataGridView client_revisions_grid;
        private System.Windows.Forms.DataGridView client_rents_grid;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.Label label7;
        private System.Windows.Forms.DataGridView changed_parts_grid;
        private System.Windows.Forms.Label label8;
    }
}