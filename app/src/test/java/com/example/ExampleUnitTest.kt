package com.example

import com.example.data.model.StageData
import com.example.data.model.Trip
import com.example.util.Formatters
import com.example.util.JsonUtil
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleUnitTest {
  @Test
  fun testShiftDistanceCalculation() {
    val trip = Trip(
      id = "TEST-TRIP",
      driverId = "DRV-1001",
      driverName = "Ahmed",
      bus = "DXB-4821",
      startOdometer = 124500.0,
      endOdometer = 124620.0
    )
    assertEquals(120.0, trip.distance ?: 0.0, 0.01)
  }

  @Test
  fun testStageSerializationAndDeserialization() {
    val stages = listOf(
      StageData("start", "Before Trip Start", 100.0, "/path/img1.jpg", 25.20, 55.27),
      StageData("dest", "Reached Destination", 150.0, "/path/img2.jpg", 25.19, 55.28)
    )
    val json = JsonUtil.serializeStages(stages)
    val restored = JsonUtil.deserializeStages(json)

    assertEquals(2, restored.size)
    assertEquals("start", restored[0].key)
    assertEquals(100.0, restored[0].odometer ?: 0.0, 0.01)
    assertEquals("/path/img1.jpg", restored[0].photoPath)
  }

  @Test
  fun testFormatters() {
    assertEquals("124,580 km", Formatters.formatKm(124580.0))
    assertEquals("185.50 AED", Formatters.formatAed(185.50))
    assertEquals("—", Formatters.formatKm(null))
  }
}
