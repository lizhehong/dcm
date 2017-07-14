package org.bdc.dcm.modbus.handler;

import org.bdc.dcm.modbus.requests.ReadFullReadHoldingRegistersRequest;

import com.digitalpetri.modbus.requests.MaskWriteRegisterRequest;
import com.digitalpetri.modbus.requests.ModbusRequest;
import com.digitalpetri.modbus.requests.ReadCoilsRequest;
import com.digitalpetri.modbus.requests.ReadDiscreteInputsRequest;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.requests.ReadInputRegistersRequest;
import com.digitalpetri.modbus.requests.WriteMultipleCoilsRequest;
import com.digitalpetri.modbus.requests.WriteMultipleRegistersRequest;
import com.digitalpetri.modbus.requests.WriteSingleCoilRequest;
import com.digitalpetri.modbus.requests.WriteSingleRegisterRequest;
import com.digitalpetri.modbus.responses.MaskWriteRegisterResponse;
import com.digitalpetri.modbus.responses.ModbusResponse;
import com.digitalpetri.modbus.responses.ReadCoilsResponse;
import com.digitalpetri.modbus.responses.ReadDiscreteInputsResponse;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import com.digitalpetri.modbus.responses.ReadInputRegistersResponse;
import com.digitalpetri.modbus.responses.WriteMultipleCoilsResponse;
import com.digitalpetri.modbus.responses.WriteMultipleRegistersResponse;
import com.digitalpetri.modbus.responses.WriteSingleCoilResponse;
import com.digitalpetri.modbus.responses.WriteSingleRegisterResponse;

import io.netty.util.ReferenceCountUtil;

public interface ServiceRequestHandler {

	default void onReadHoldingRegisters(
			ServiceRequest<ReadFullReadHoldingRegistersRequest, ReadHoldingRegistersResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onReadInputRegisters(ServiceRequest<ReadInputRegistersRequest, ReadInputRegistersResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onReadCoils(ServiceRequest<ReadCoilsRequest, ReadCoilsResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onReadDiscreteInputs(ServiceRequest<ReadDiscreteInputsRequest, ReadDiscreteInputsResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onWriteSingleCoil(ServiceRequest<WriteSingleCoilRequest, WriteSingleCoilResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onWriteSingleRegister(
			ServiceRequest<WriteSingleRegisterRequest, WriteSingleRegisterResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onWriteMultipleCoils(ServiceRequest<WriteMultipleCoilsRequest, WriteMultipleCoilsResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onWriteMultipleRegisters(
			ServiceRequest<WriteMultipleRegistersRequest, WriteMultipleRegistersResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	default void onMaskWriteRegister(ServiceRequest<MaskWriteRegisterRequest, MaskWriteRegisterResponse> service) {

		ReferenceCountUtil.release(service.getRequest());
	}

	public static interface ServiceRequest<Request extends ModbusRequest, Response extends ModbusResponse> {

		/**
		 * @return the request to service.
		 */
		Request getRequest();
		
		   /**
         * Send a normal response.
         *
         * @param response the service response
         */
        void sendResponse(Response response);


	}

}
