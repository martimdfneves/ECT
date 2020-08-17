using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Shopping_Agent
{
    /// <summary>
    /// Interaction logic for Account.xaml
    /// </summary>
    public partial class Account : Page
    {
        public Account()
        {
            InitializeComponent();
        }

        private void btnOrders_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Orders ord_page = new Orders();
            navService.Navigate(ord_page);
        }

        private void btnHome_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Client_Home cl_page = new Client_Home();
            navService.Navigate(cl_page);
        }

        private void btnStorage_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Storage st_page = new Storage();
            navService.Navigate(st_page);
        }

        private void btnPurchase_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Purchase pur_page = new Purchase();
            navService.Navigate(pur_page);
        }

        private void btnAccount_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Account acc_page = new Account();
            navService.Navigate(acc_page);
        }

        private void btnLogout_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Login log_page = new Login();
            navService.Navigate(log_page);
        }

        private void btnEdit_Click(object sender, RoutedEventArgs e)
        {
            UsernameTextBox2.IsReadOnly = bool.Parse("False");
            EmailTextBox2.IsReadOnly = bool.Parse("False");
            AddressTextBox2.IsReadOnly = bool.Parse("False");
            ContactTextBox2.IsReadOnly = bool.Parse("False");
            LanguageTextBox2.IsReadOnly = bool.Parse("False");
        }

        private void btnSave_Click(object sender, RoutedEventArgs e)
        {
            UsernameTextBox2.IsReadOnly = bool.Parse("True");
            EmailTextBox2.IsReadOnly = bool.Parse("True");
            AddressTextBox2.IsReadOnly = bool.Parse("True");
            ContactTextBox2.IsReadOnly = bool.Parse("True");
            LanguageTextBox2.IsReadOnly = bool.Parse("True");
        }

        private void LanguageTextBox1_TextChanged(object sender, TextChangedEventArgs e)
        {

        }

        private void LanguageTextBox2_TextChanged(object sender, TextChangedEventArgs e)
        {

        }
    }
}
