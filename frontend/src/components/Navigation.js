import logo from '../logo.svg';
import { Container, Nav, Navbar, NavDropdown} from "react-bootstrap";
import { useEffect, useState } from 'react';
import { fetchUser } from '../api/UserAPI';

export default function TestPage() {
    const [user, setUser] = useState({});
    const ACCESS_TOKEN = localStorage.getItem('accessToken');

    useEffect(() => {
        if (ACCESS_TOKEN) {
            fetchUser()
            .then((response) => {
                setUser(response);
            }).catch((error) => {
                console.log(error);
            });
        }
    }, [ACCESS_TOKEN]);

    const handleLogout = async () => {
        localStorage.clear();
    }

    return (
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
            <Container>
                <Navbar.Brand href="/home">
                    <img src={logo} width="40" height="35" alt="" />
                    Home
                </Navbar.Brand>

                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto" alt="Nav Empty Space">

                    </Nav>
                    <Nav>
                        <Nav.Link href="/home">Home</Nav.Link>

                        <NavDropdown title="DropDown1" id="collasible-nav-dropdown">
                            <NavDropdown.Item href="/dropdown1/menu1">Menu1</NavDropdown.Item>
                            <NavDropdown.Item href="/dropdown1/menu2">Menu2</NavDropdown.Item>
                            <NavDropdown.Item href="/dropdown1/menu3">Menu3</NavDropdown.Item>
                        </NavDropdown>
                        <NavDropdown title="DropDown2" id="collasible-nav-dropdown">
                            <NavDropdown.Item href="/dropdown2/menu1">Menu1</NavDropdown.Item>
                            <NavDropdown.Item href="/dropdown2/menu2">Menu2</NavDropdown.Item>
                            <NavDropdown.Item href="/dropdown2/menu3">Menu3</NavDropdown.Item>
                        </NavDropdown>

                        {ACCESS_TOKEN
                        ?
                        <NavDropdown title={user.username + "님 환영합니다"} id="collasible-nav-dropdown">
                            <NavDropdown.Item href="/my-page">MyPage</NavDropdown.Item>
                            <NavDropdown.Item href="/" onClick={handleLogout}>로그아웃</NavDropdown.Item>
                        </NavDropdown>
                        :
                        <NavDropdown title="Login/SignUp" id="collasible-nav-dropdown">
                            <NavDropdown.Item href="/login">Login</NavDropdown.Item>
                            <NavDropdown.Item href="/signup">SignUp</NavDropdown.Item>
                        </NavDropdown>
                        }
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}