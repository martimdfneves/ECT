using System;

namespace Motoshop
{
    partial class Staff
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
            this.stand_grid_view = new System.Windows.Forms.DataGridView();
            this.salesman_grid_view = new System.Windows.Forms.DataGridView();
            this.label1 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.workshop_grid_view = new System.Windows.Forms.DataGridView();
            this.label3 = new System.Windows.Forms.Label();
            this.mechanic_grid_view = new System.Windows.Forms.DataGridView();
            this.label4 = new System.Windows.Forms.Label();
            this.add_stand = new System.Windows.Forms.Button();
            this.rem_stand = new System.Windows.Forms.Button();
            this.add_workshop = new System.Windows.Forms.Button();
            this.rem_workshop = new System.Windows.Forms.Button();
            this.add_salesman = new System.Windows.Forms.Button();
            this.rem_salesman = new System.Windows.Forms.Button();
            this.rem_mechanic = new System.Windows.Forms.Button();
            this.add_mechanic = new System.Windows.Forms.Button();
            this.sales_grid_view = new System.Windows.Forms.DataGridView();
            this.label5 = new System.Windows.Forms.Label();
            this.label6 = new System.Windows.Forms.Label();
            this.revisions_grid_view = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.stand_grid_view)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.salesman_grid_view)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.workshop_grid_view)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.mechanic_grid_view)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.sales_grid_view)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.revisions_grid_view)).BeginInit();
            this.SuspendLayout();
            // 
            // stand_grid_view
            // 
            this.stand_grid_view.AllowUserToAddRows = false;
            this.stand_grid_view.AllowUserToDeleteRows = false;
            this.stand_grid_view.AllowUserToResizeRows = false;
            this.stand_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.stand_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.stand_grid_view.Location = new System.Drawing.Point(20, 50);
            this.stand_grid_view.MultiSelect = false;
            this.stand_grid_view.Name = "stand_grid_view";
            this.stand_grid_view.ReadOnly = true;
            this.stand_grid_view.RowHeadersVisible = false;
            this.stand_grid_view.Size = new System.Drawing.Size(281, 200);
            this.stand_grid_view.TabIndex = 0;
            this.stand_grid_view.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.stand_grid_view_CellClick);
            // 
            // salesman_grid_view
            // 
            this.salesman_grid_view.AllowUserToAddRows = false;
            this.salesman_grid_view.AllowUserToDeleteRows = false;
            this.salesman_grid_view.AllowUserToResizeRows = false;
            this.salesman_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.salesman_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.salesman_grid_view.Location = new System.Drawing.Point(333, 50);
            this.salesman_grid_view.MultiSelect = false;
            this.salesman_grid_view.Name = "salesman_grid_view";
            this.salesman_grid_view.ReadOnly = true;
            this.salesman_grid_view.RowHeadersVisible = false;
            this.salesman_grid_view.Size = new System.Drawing.Size(326, 200);
            this.salesman_grid_view.TabIndex = 1;
            this.salesman_grid_view.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.salesman_grid_view_CellClick);
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label1.Location = new System.Drawing.Point(14, 10);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(99, 31);
            this.label1.TabIndex = 2;
            this.label1.Text = "Stands";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(327, 10);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(134, 31);
            this.label2.TabIndex = 3;
            this.label2.Text = "Salesmen";
            // 
            // workshop_grid_view
            // 
            this.workshop_grid_view.AllowUserToAddRows = false;
            this.workshop_grid_view.AllowUserToDeleteRows = false;
            this.workshop_grid_view.AllowUserToResizeRows = false;
            this.workshop_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.workshop_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.workshop_grid_view.Location = new System.Drawing.Point(20, 350);
            this.workshop_grid_view.MultiSelect = false;
            this.workshop_grid_view.Name = "workshop_grid_view";
            this.workshop_grid_view.ReadOnly = true;
            this.workshop_grid_view.RowHeadersVisible = false;
            this.workshop_grid_view.Size = new System.Drawing.Size(281, 213);
            this.workshop_grid_view.TabIndex = 4;
            this.workshop_grid_view.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.workshop_grid_view_CellClick);
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label3.Location = new System.Drawing.Point(14, 305);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(150, 31);
            this.label3.TabIndex = 5;
            this.label3.Text = "Workshops";
            // 
            // mechanic_grid_view
            // 
            this.mechanic_grid_view.AllowUserToAddRows = false;
            this.mechanic_grid_view.AllowUserToDeleteRows = false;
            this.mechanic_grid_view.AllowUserToResizeRows = false;
            this.mechanic_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.mechanic_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.mechanic_grid_view.Location = new System.Drawing.Point(333, 350);
            this.mechanic_grid_view.MultiSelect = false;
            this.mechanic_grid_view.Name = "mechanic_grid_view";
            this.mechanic_grid_view.ReadOnly = true;
            this.mechanic_grid_view.RowHeadersVisible = false;
            this.mechanic_grid_view.Size = new System.Drawing.Size(326, 213);
            this.mechanic_grid_view.TabIndex = 6;
            this.mechanic_grid_view.CellClick += new System.Windows.Forms.DataGridViewCellEventHandler(this.mechanic_grid_view_CellClick);
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label4.Location = new System.Drawing.Point(327, 305);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(144, 31);
            this.label4.TabIndex = 7;
            this.label4.Text = "Mechanics";
            // 
            // add_stand
            // 
            this.add_stand.Location = new System.Drawing.Point(20, 256);
            this.add_stand.Name = "add_stand";
            this.add_stand.Size = new System.Drawing.Size(144, 35);
            this.add_stand.TabIndex = 8;
            this.add_stand.Text = "Add";
            this.add_stand.UseVisualStyleBackColor = true;
            this.add_stand.Click += new System.EventHandler(this.add_stand_Click);
            // 
            // rem_stand
            // 
            this.rem_stand.Location = new System.Drawing.Point(171, 256);
            this.rem_stand.Name = "rem_stand";
            this.rem_stand.Size = new System.Drawing.Size(130, 35);
            this.rem_stand.TabIndex = 9;
            this.rem_stand.Text = "Remove";
            this.rem_stand.UseVisualStyleBackColor = true;
            this.rem_stand.Click += new System.EventHandler(this.rem_stand_Click);
            // 
            // add_workshop
            // 
            this.add_workshop.Location = new System.Drawing.Point(20, 569);
            this.add_workshop.Name = "add_workshop";
            this.add_workshop.Size = new System.Drawing.Size(144, 35);
            this.add_workshop.TabIndex = 10;
            this.add_workshop.Text = "Add";
            this.add_workshop.UseVisualStyleBackColor = true;
            this.add_workshop.Click += new System.EventHandler(this.add_workshop_Click);
            // 
            // rem_workshop
            // 
            this.rem_workshop.Location = new System.Drawing.Point(171, 569);
            this.rem_workshop.Name = "rem_workshop";
            this.rem_workshop.Size = new System.Drawing.Size(130, 35);
            this.rem_workshop.TabIndex = 11;
            this.rem_workshop.Text = "Remove";
            this.rem_workshop.UseVisualStyleBackColor = true;
            this.rem_workshop.Click += new System.EventHandler(this.rem_workshop_Click);
            // 
            // add_salesman
            // 
            this.add_salesman.Location = new System.Drawing.Point(333, 256);
            this.add_salesman.Name = "add_salesman";
            this.add_salesman.Size = new System.Drawing.Size(160, 35);
            this.add_salesman.TabIndex = 12;
            this.add_salesman.Text = "Add";
            this.add_salesman.UseVisualStyleBackColor = true;
            this.add_salesman.Click += new System.EventHandler(this.add_salesman_Click);
            // 
            // rem_salesman
            // 
            this.rem_salesman.Location = new System.Drawing.Point(499, 256);
            this.rem_salesman.Name = "rem_salesman";
            this.rem_salesman.Size = new System.Drawing.Size(160, 35);
            this.rem_salesman.TabIndex = 13;
            this.rem_salesman.Text = "Remove";
            this.rem_salesman.UseVisualStyleBackColor = true;
            this.rem_salesman.Click += new System.EventHandler(this.rem_salesman_Click);
            // 
            // rem_mechanic
            // 
            this.rem_mechanic.Location = new System.Drawing.Point(499, 569);
            this.rem_mechanic.Name = "rem_mechanic";
            this.rem_mechanic.Size = new System.Drawing.Size(160, 35);
            this.rem_mechanic.TabIndex = 16;
            this.rem_mechanic.Text = "Remove";
            this.rem_mechanic.UseVisualStyleBackColor = true;
            this.rem_mechanic.Click += new System.EventHandler(this.rem_mechanic_Click);
            // 
            // add_mechanic
            // 
            this.add_mechanic.Location = new System.Drawing.Point(333, 569);
            this.add_mechanic.Name = "add_mechanic";
            this.add_mechanic.Size = new System.Drawing.Size(160, 35);
            this.add_mechanic.TabIndex = 15;
            this.add_mechanic.Text = "Add";
            this.add_mechanic.UseVisualStyleBackColor = true;
            this.add_mechanic.Click += new System.EventHandler(this.add_mechanic_Click);
            // 
            // sales_grid_view
            // 
            this.sales_grid_view.AllowUserToAddRows = false;
            this.sales_grid_view.AllowUserToDeleteRows = false;
            this.sales_grid_view.AllowUserToResizeRows = false;
            this.sales_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.sales_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.sales_grid_view.Location = new System.Drawing.Point(689, 50);
            this.sales_grid_view.MultiSelect = false;
            this.sales_grid_view.Name = "sales_grid_view";
            this.sales_grid_view.ReadOnly = true;
            this.sales_grid_view.RowHeadersVisible = false;
            this.sales_grid_view.Size = new System.Drawing.Size(548, 241);
            this.sales_grid_view.TabIndex = 18;
            // 
            // label5
            // 
            this.label5.AutoSize = true;
            this.label5.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label5.Location = new System.Drawing.Point(683, 10);
            this.label5.Name = "label5";
            this.label5.Size = new System.Drawing.Size(82, 31);
            this.label5.TabIndex = 19;
            this.label5.Text = "Sales";
            // 
            // label6
            // 
            this.label6.AutoSize = true;
            this.label6.Font = new System.Drawing.Font("Microsoft Sans Serif", 20.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label6.Location = new System.Drawing.Point(683, 305);
            this.label6.Name = "label6";
            this.label6.Size = new System.Drawing.Size(133, 31);
            this.label6.TabIndex = 21;
            this.label6.Text = "Revisions";
            // 
            // revisions_grid_view
            // 
            this.revisions_grid_view.AllowUserToAddRows = false;
            this.revisions_grid_view.AllowUserToDeleteRows = false;
            this.revisions_grid_view.AllowUserToResizeRows = false;
            this.revisions_grid_view.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.revisions_grid_view.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.revisions_grid_view.Location = new System.Drawing.Point(689, 350);
            this.revisions_grid_view.MultiSelect = false;
            this.revisions_grid_view.Name = "revisions_grid_view";
            this.revisions_grid_view.ReadOnly = true;
            this.revisions_grid_view.RowHeadersVisible = false;
            this.revisions_grid_view.Size = new System.Drawing.Size(548, 254);
            this.revisions_grid_view.TabIndex = 20;
            // 
            // Staff
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1264, 622);
            this.Controls.Add(this.label6);
            this.Controls.Add(this.revisions_grid_view);
            this.Controls.Add(this.label5);
            this.Controls.Add(this.sales_grid_view);
            this.Controls.Add(this.rem_mechanic);
            this.Controls.Add(this.add_mechanic);
            this.Controls.Add(this.rem_salesman);
            this.Controls.Add(this.add_salesman);
            this.Controls.Add(this.rem_workshop);
            this.Controls.Add(this.add_workshop);
            this.Controls.Add(this.rem_stand);
            this.Controls.Add(this.add_stand);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.mechanic_grid_view);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.workshop_grid_view);
            this.Controls.Add(this.salesman_grid_view);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.stand_grid_view);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "Staff";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.staff_load);
            this.Click += new System.EventHandler(this.form_Click);
            ((System.ComponentModel.ISupportInitialize)(this.stand_grid_view)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.salesman_grid_view)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.workshop_grid_view)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.mechanic_grid_view)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.sales_grid_view)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.revisions_grid_view)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView stand_grid_view;
        private System.Windows.Forms.DataGridView salesman_grid_view;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.DataGridView workshop_grid_view;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.DataGridView mechanic_grid_view;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button add_stand;
        private System.Windows.Forms.Button rem_stand;
        private System.Windows.Forms.Button add_workshop;
        private System.Windows.Forms.Button rem_workshop;
        private System.Windows.Forms.Button add_salesman;
        private System.Windows.Forms.Button rem_salesman;
        private System.Windows.Forms.Button rem_mechanic;
        private System.Windows.Forms.Button add_mechanic;
        private System.Windows.Forms.DataGridView sales_grid_view;
        private System.Windows.Forms.Label label5;
        private System.Windows.Forms.Label label6;
        private System.Windows.Forms.DataGridView revisions_grid_view;
    }
}