package com.practo.dao.impl;

import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.practo.dao.interfaces.TransactionDao;
import com.practo.dto.TransactionDto;
import com.practo.entity.TransactionEntity;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class TransactionDaoImpl implements TransactionDao 
{
	private final ModelMapper modelMapper;
	
    private final NamedParameterJdbcTemplate jdbcTemplate;
	
	public TransactionDaoImpl(ModelMapper modelMapper,
			NamedParameterJdbcTemplate jdbcTemplate) {
		this.modelMapper = modelMapper;
		this.jdbcTemplate=jdbcTemplate;
	}


	@Override
	public TransactionDto createTransaction(TransactionDto transactionDto)
	{
		log.info("Creating transactionDto in db:"+transactionDto);
		// TODO Auto-generated method stub
		 
		TransactionEntity txnEntity=modelMapper.map(transactionDto, TransactionEntity.class);
		log.info("convert to Entity txnEntity:"+txnEntity);
		
		String sql = "INSERT INTO payments.`Transaction` (userId, paymentMethodId, providerId, paymentTypeId, txnStatusId, " +
				"amount, currency, merchantTxnReference, txnReference, providerReference, errorCode, errorMessage, retryCount) " +
				"VALUES (:userId, :paymentMethodId, :providerId, :paymentTypeId, :txnStatusId, :amount, :currency, " +
				":merchantTxnReference, :txnReference, :providerReference, :errorCode, :errorMessage, :retryCount)";

		SqlParameterSource params = new BeanPropertySqlParameterSource(txnEntity);
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
		
		int id = keyHolder.getKey() != null ? keyHolder.getKey().intValue() : -1;
		transactionDto.setId(id);
		
		log.info("Transaction created in DB||txn id:{}|txnReference:{}",
				transactionDto.getId(),
				transactionDto.getTxnReference());
			
		return transactionDto;
	}
	

	@Override
	public TransactionDto getTransactionByTxnReference(String txnReference)
	{
		String sql = "SELECT * FROM payments.`Transaction` WHERE txnReference = :txnReference";
	
	MapSqlParameterSource params=new MapSqlParameterSource();
	params.addValue("txnReference",txnReference);
	log.info("**Recevied param:"+params);
	try {
		TransactionEntity transaction=jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(TransactionEntity.class));
		
		TransactionDto txnDto=modelMapper.map(transaction,TransactionDto.class);
		log.info("**received data from DB TranactionDTO:"+txnDto);
		return txnDto;
		
	}catch(Exception e) {
		log.info("Error occur in catch block"+txnReference);
		return null;
	}
	
	}
	
	@Override
	public TransactionDto updateTransactionStatusDetails (TransactionDto txnDto) 
	{
		log.info("in updateTransactionStatusDetails :"+txnDto);
		String sql = "UPDATE payments.`Transaction` SET txnStatusId=:txnStatusId,providerReference=:providerReference,errorCode=:errorCode,errorMessage=:errorMessage WHERE txnReference=:txnReference";

		TransactionEntity transaction = modelMapper.map(txnDto, TransactionEntity.class);
		
		SqlParameterSource params = new BeanPropertySqlParameterSource(transaction);

		jdbcTemplate.update(sql, params); 

		log.info("Transaction status updated in DB||txnDto: ()", txnDto);
		
		return txnDto;
}


	@Override
	public TransactionDto getTransactionByProviderReference(String ProviderReference) {
		String sql = "SELECT * FROM payments.`Transaction` WHERE ProviderReference = :ProviderReference";
		
		MapSqlParameterSource params=new MapSqlParameterSource();
		params.addValue("ProviderReference",ProviderReference);
		log.info("**Recevied param:"+params);
		try {
			TransactionEntity transaction=jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(TransactionEntity.class));
			
			TransactionDto txnDto=modelMapper.map(transaction,TransactionDto.class);
			log.info("**received data from DB TranactionDTO:"+txnDto);
			return txnDto;
			
		}catch(Exception e) {
			log.info("Error occur in catch block"+ProviderReference);
			return null;
		}
	}

}