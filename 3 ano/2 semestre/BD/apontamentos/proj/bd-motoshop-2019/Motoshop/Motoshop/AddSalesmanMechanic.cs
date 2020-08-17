using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Motoshop
{
    public partial class AddSalesmanMechanic : Form
    {
        public String name = "";
        public String address = "";

        public AddSalesmanMechanic(bool salesman)
        {
            InitializeComponent();
            if (salesman)
            {
                this.Text = "Add Salesman";
                this.label.Text = "Add new Salesman";
            }
            else
            {
                this.Text = "Add Mechanic";
                this.label.Text = "Add new Mechanic";
            }
        }

        private void add_btn_Click(object sender, EventArgs e)
        {
            if (this.name_tb.Text != "" && this.address_tb.Text != "")
            {
                this.name = this.name_tb.Text;
                this.address = this.address_tb.Text;
                this.Close();
            }
        }

        private void cancel_btn_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
