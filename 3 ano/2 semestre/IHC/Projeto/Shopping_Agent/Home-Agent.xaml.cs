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
    /// Interaction logic for Home_Agent.xaml
    /// </summary>
    public partial class Home_Agent : Page
    {
        public Home_Agent()
        {
            InitializeComponent();
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            Login loginPage = new Login();
            navService.Navigate(loginPage);

        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            NavigationService navService = NavigationService.GetNavigationService(this);
            SignUp signPage = new SignUp();
            navService.Navigate(signPage);


        }
    }
}
