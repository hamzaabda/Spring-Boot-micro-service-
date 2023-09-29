export interface AuthResponseData {
    errormessage: string;
    successmessage: string;
    tokens: {
      access_token: string;
      refresh_token: string;
    };
  }