using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Motoshop
{
    public partial class Login : Form
    {
        private SqlConnection cn;
        private DatabaseHandler db;
        public static int logged_in_staff_member;

        public Login()
        {
            InitializeComponent();
            db = new DatabaseHandler();
            db.initSGBDConnection();
        }

        private void loadSalesmen()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM STAFF_SALESMEN", cn);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            staff_list.DataSource = ds.Tables[0];
            staff_list.Columns[0].HeaderText = "Number";
            staff_list.Columns[1].HeaderText = "Member";
            staff_list.Columns[2].HeaderText = "Stand";

            cn.Close();

        }

        private void loadMechanics()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM STAFF_MECHANICS", cn);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            staff_list.DataSource = ds.Tables[0];
            staff_list.Columns[0].HeaderText = "Number";
            staff_list.Columns[1].HeaderText = "Member";
            staff_list.Columns[2].HeaderText = "Workshop";

            cn.Close();
        }

        private void btn_salesman_Click(object sender, EventArgs e)
        {
            btn_salesman.Visible = false;
            btn_mechanic.Visible = false;
            btn_login.Visible = true;
            btn_back.Visible = true;
            staff_list.Visible = true;
            loadSalesmen();
        }

        private void btn_mechanic_Click(object sender, EventArgs e)
        {
            btn_salesman.Visible = false;
            btn_mechanic.Visible = false;
            btn_login.Visible = true;
            btn_back.Visible = true;
            staff_list.Visible = true;
            loadMechanics();
        }

        private void btn_login_Click(object sender, EventArgs e)
        {
            if(staff_list.SelectedCells.Count > 0) {
                int row_idx = staff_list.SelectedCells[0].RowIndex;
                DataGridViewRow selectedRow = staff_list.Rows[row_idx];
                int staff_number = Convert.ToInt32(selectedRow.Cells[0].Value);
                Login.logged_in_staff_member = staff_number;
                this.Hide();
                MainApp m = new MainApp();
                m.Show();
            }

        }

        private void btn_back_Click(object sender, EventArgs e)
        {
            btn_salesman.Visible = true;
            btn_mechanic.Visible = true;
            btn_login.Visible = false;
            btn_back.Visible = false;
            staff_list.Visible = false;
        }

        private void Login_Load(object sender, EventArgs e)
        {

        }
    }
}
