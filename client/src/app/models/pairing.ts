import { Time } from "@angular/common";
import { Employee } from "./employee";

export class Pairing {
    constructor(
        public pairingId: string,
        public employeeId: string,
        public pairedEmployeeId: string,
        public pairingDate: Date,
        public lunchDate: Date,
        public lunchTime: Time,
        public lunchVenue: string,
        public pairedEmployee: Employee
    ) { };
}