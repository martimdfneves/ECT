import axios from 'axios'

const FIREFIGHTERS_API_BASE_URL = "http://192.168.160.87:11003/fighters/env";

class FireServices {
    getFireEnv() {
        return axios.get(FIREFIGHTERS_API_BASE_URL);
    }
}

export default new FireServices();
