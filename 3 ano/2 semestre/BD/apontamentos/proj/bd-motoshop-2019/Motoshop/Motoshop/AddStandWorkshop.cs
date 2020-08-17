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
    public partial class AddStandWorkshop : Form
    {
        public String localization = "";

        public AddStandWorkshop(bool stand)
        {
            InitializeComponent();
            if (stand)
            {
                this.Text = "Add Stand";
                this.label.Text = "Add new Stand";
            }
            else
            {
                this.Text = "Add Workshop";
                this.label.Text = "Add new Workshop";
            }
        }

        private void add_btn_Click(object sender, EventArgs e)
        {
            if (this.local_tb.Text != "")
            {
                localization = local_tb.Text;
                this.Close();
            }
        }

        private void cancel_btn_Click(object sender, EventArgs e)
        {
            this.Close();
        }
    }
}
