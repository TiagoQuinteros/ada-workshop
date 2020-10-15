export interface ICuenta {
  id?: number;
  balance?: number;
  nombre?: string;
  apellido?: string;
}

export class Cuenta implements ICuenta {
  constructor(public id?: number, public balance?: number, public nombre?: string, public apellido?: string) {}
}
