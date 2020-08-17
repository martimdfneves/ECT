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
    public partial class Staff : Form
    {
        private SqlConnection cn;
        private DatabaseHandler db;

        public Staff()
        {
            InitializeComponent();
            db = new DatabaseHandler();
            db.initSGBDConnection();
        }

        private void staff_load(object sender, EventArgs e)
        {
            loadStands();
            loadWorkshops();
        }

        private void loadStands()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM STAND", cn);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            this.stand_grid_view.DataSource = ds.Tables[0];
            this.stand_grid_view.Columns[0].HeaderText = "ID";
            this.stand_grid_view.Columns[1].HeaderText = "Localization";
            this.stand_grid_view.ClearSelection();
            this.salesman_grid_view.DataSource = null;
            this.sales_grid_view.DataSource = null;

            cn.Close();

            this.rem_stand.Enabled = false;
            this.add_salesman.Enabled = false;
            this.rem_salesman.Enabled = false;
        }

        private void loadWorkshops()
        {
            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            DataSet ds = new DataSet();
            SqlCommand cmd = new SqlCommand("SELECT * FROM WORKSHOP", cn);
            var adapter = new SqlDataAdapter(cmd);

            //SqlDataReader reader = cmd.ExecuteReader();
            adapter.Fill(ds);
            this.workshop_grid_view.DataSource = ds.Tables[0];
            this.workshop_grid_view.Columns[0].HeaderText = "ID";
            this.workshop_grid_view.Columns[1].HeaderText = "Localization";
            this.workshop_grid_view.ClearSelection();
            this.mechanic_grid_view.DataSource = null;
            this.revisions_grid_view.DataSource = null;

            cn.Close();

            this.rem_workshop.Enabled = false;
            this.add_mechanic.Enabled = false;
            this.rem_mechanic.Enabled = false;
        }

        private void stand_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                this.rem_stand.Enabled = true;
                this.add_salesman.Enabled = true;

                this.stand_grid_view.Rows[e.RowIndex].Selected = true;
                String stand = this.stand_grid_view.Rows[e.RowIndex].Cells[0].FormattedValue.ToString();

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                DataSet ds = new DataSet();
                SqlCommand cmd = new SqlCommand("SELECT * FROM SALESMAN_FROM_STAND(@Stand)", cn);
                cmd.Parameters.AddWithValue("Stand", stand);
                var adapter = new SqlDataAdapter(cmd);
                adapter.Fill(ds);
                this.salesman_grid_view.DataSource = ds.Tables[0];
                this.salesman_grid_view.ClearSelection();

                cn.Close();
            }
        }

        private void workshop_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                this.rem_workshop.Enabled = true;
                this.add_mechanic.Enabled = true;

                this.workshop_grid_view.Rows[e.RowIndex].Selected = true;
                String workshop = this.workshop_grid_view.Rows[e.RowIndex].Cells[0].FormattedValue.ToString();

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                DataSet ds = new DataSet();
                SqlCommand cmd = new SqlCommand("SELECT * FROM MECHANIC_FROM_WORKSHOP(@Stand)", cn);
                cmd.Parameters.AddWithValue("Stand", workshop);
                var adapter = new SqlDataAdapter(cmd);
                adapter.Fill(ds);
                this.mechanic_grid_view.DataSource = ds.Tables[0];
                this.mechanic_grid_view.ClearSelection();

                cn.Close();
            }
        }

        private void salesman_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                this.rem_salesman.Enabled = true;

                this.salesman_grid_view.Rows[e.RowIndex].Selected = true;
                String seller = this.salesman_grid_view.Rows[e.RowIndex].Cells[0].FormattedValue.ToString();

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                DataSet ds = new DataSet();
                SqlCommand cmd = new SqlCommand("SELECT * FROM SALES_FROM_SALESMAN(@Seller)", cn);
                cmd.Parameters.AddWithValue("Seller", seller);
                var adapter = new SqlDataAdapter(cmd);
                adapter.Fill(ds);
                this.sales_grid_view.DataSource = ds.Tables[0];
                this.sales_grid_view.ClearSelection();

                cn.Close();
            }
        }

        private void mechanic_grid_view_CellClick(object sender, DataGridViewCellEventArgs e)
        {
            if (e.RowIndex >= 0)
            {
                this.rem_mechanic.Enabled = true;

                this.mechanic_grid_view.Rows[e.RowIndex].Selected = true;
                String mechanic = this.mechanic_grid_view.Rows[e.RowIndex].Cells[0].FormattedValue.ToString();

                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                DataSet ds = new DataSet();
                SqlCommand cmd = new SqlCommand("SELECT * FROM REVISIONS_FROM_MECHANIC(@Mechanic)", cn);
                cmd.Parameters.AddWithValue("Mechanic", mechanic);
                var adapter = new SqlDataAdapter(cmd);
                adapter.Fill(ds);
                this.revisions_grid_view.DataSource = ds.Tables[0];
                this.revisions_grid_view.ClearSelection();

                cn.Close();
            }
        }

        private void add_stand_Click(object sender, EventArgs e)
        {
            AddStandWorkshop add = new AddStandWorkshop(true);
            add.FormClosed += new FormClosedEventHandler(add_stand_close);
            add.ShowDialog();
        }

        private void add_stand_close(object sender, FormClosedEventArgs e)
        {
            String localization = ((AddStandWorkshop)sender).localization;
            if (localization != "")
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("EXEC ms_ADD_STAND @Localization", cn);
                cmd.Parameters.AddWithValue("Localization", localization);

                try
                {
                    cmd.ExecuteNonQuery();
                    loadStands();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }

                cn.Close();
            }
        }

        private void rem_stand_Click(object sender, EventArgs e)
        {
            String id = this.stand_grid_view.Rows[this.stand_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("EXEC ms_REM_STAND @ID", cn);
            cmd.Parameters.AddWithValue("ID", id);

            try
            {
                cmd.ExecuteNonQuery();
                loadStands();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            cn.Close();
        }

        private void add_workshop_Click(object sender, EventArgs e)
        {
            AddStandWorkshop add = new AddStandWorkshop(true);
            add.FormClosed += new FormClosedEventHandler(add_workshop_close);
            add.ShowDialog();
        }

        private void add_workshop_close(object sender, FormClosedEventArgs e)
        {
            String localization = ((AddStandWorkshop)sender).localization;
            if (localization != "")
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("EXEC ms_ADD_WORKSHOP @Localization", cn);
                cmd.Parameters.AddWithValue("Localization", localization);

                try
                {
                    cmd.ExecuteNonQuery();
                    loadWorkshops();
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }

                cn.Close();
            }
        }

        private void rem_workshop_Click(object sender, EventArgs e)
        {
            String id = this.workshop_grid_view.Rows[this.workshop_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("EXEC ms_REM_WORKSHOP @ID", cn);
            cmd.Parameters.AddWithValue("ID", id);

            try
            {
                cmd.ExecuteNonQuery();
                loadWorkshops();
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            cn.Close();
        }

        private void add_salesman_Click(object sender, EventArgs e)
        {
            AddSalesmanMechanic add = new AddSalesmanMechanic(true);
            add.FormClosed += new FormClosedEventHandler(add_salesman_close);
            add.ShowDialog();
        }

        private void add_salesman_close(object sender, FormClosedEventArgs e)
        {
            String name = ((AddSalesmanMechanic)sender).name;
            String address = ((AddSalesmanMechanic)sender).address;
            String stand = this.stand_grid_view.Rows[this.stand_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();

            if (name != "" && address != "" && stand != "")
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("EXEC ms_ADD_SALESMAN @Name, @Address, @Stand", cn);
                cmd.Parameters.AddWithValue("Name", name);
                cmd.Parameters.AddWithValue("Address", address);
                cmd.Parameters.AddWithValue("Stand", stand);

                try
                {
                    cmd.ExecuteNonQuery();
                    stand_grid_view_CellClick(this.stand_grid_view, new DataGridViewCellEventArgs(0, this.stand_grid_view.CurrentCell.RowIndex));
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }

                cn.Close();
            }
        }

        private void add_mechanic_Click(object sender, EventArgs e)
        {
            AddSalesmanMechanic add = new AddSalesmanMechanic(false);
            add.FormClosed += new FormClosedEventHandler(add_mechanic_close);
            add.ShowDialog();
        }

        private void add_mechanic_close(object sender, FormClosedEventArgs e)
        {
            String name = ((AddSalesmanMechanic)sender).name;
            String address = ((AddSalesmanMechanic)sender).address;
            String workshop = this.workshop_grid_view.Rows[this.workshop_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();

            if (name != "" && address != "" && workshop != "")
            {
                cn = db.getSGBDConnection();

                if (!db.verifySGBDConnection())
                    return;

                SqlCommand cmd = new SqlCommand("EXEC ms_ADD_MECHANIC @Name, @Address, @Workshop", cn);
                cmd.Parameters.AddWithValue("Name", name);
                cmd.Parameters.AddWithValue("Address", address);
                cmd.Parameters.AddWithValue("workshop", workshop);

                try
                {
                    cmd.ExecuteNonQuery();
                    workshop_grid_view_CellClick(this.workshop_grid_view, new DataGridViewCellEventArgs(0, this.workshop_grid_view.CurrentCell.RowIndex));
                }
                catch (Exception ex)
                {
                    MessageBox.Show(ex.Message);
                }

                cn.Close();
            }

        }

        private void rem_salesman_Click(object sender, EventArgs e)
        {
            String id = this.salesman_grid_view.Rows[this.salesman_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("EXEC ms_REM_SALESMAN @Number", cn);
            cmd.Parameters.AddWithValue("Number", id);

            try
            {
                cmd.ExecuteNonQuery();
                stand_grid_view_CellClick(this.stand_grid_view, new DataGridViewCellEventArgs(0, this.stand_grid_view.CurrentCell.RowIndex));
                this.sales_grid_view.DataSource = null;
                this.rem_salesman.Enabled = false;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            cn.Close();
        }

        private void rem_mechanic_Click(object sender, EventArgs e)
        {
            String id = this.mechanic_grid_view.Rows[this.mechanic_grid_view.CurrentCell.RowIndex].Cells[0].FormattedValue.ToString();

            cn = db.getSGBDConnection();

            if (!db.verifySGBDConnection())
                return;

            SqlCommand cmd = new SqlCommand("EXEC ms_REM_mechanic @Number", cn);
            cmd.Parameters.AddWithValue("Number", id);

            try
            {
                cmd.ExecuteNonQuery();
                workshop_grid_view_CellClick(this.workshop_grid_view, new DataGridViewCellEventArgs(0, this.workshop_grid_view.CurrentCell.RowIndex));
                this.revisions_grid_view.DataSource = null;
                this.rem_mechanic.Enabled = false;
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            cn.Close();
        }

        private void form_Click(object sender, EventArgs e)
        {
            loadStands();
            loadWorkshops();
        }
    }
}
